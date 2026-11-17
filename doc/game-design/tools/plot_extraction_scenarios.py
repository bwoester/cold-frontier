"""Reproduce hypothetical round-scale extraction scenarios (Python 3 + matplotlib).

Run: python doc/game-design/tools/plot_extraction_scenarios.py
Time x=t/T, capacity m=P/P0, intensity alpha=P0*T/S. No game balancing defaults.
"""
from pathlib import Path
import math
import matplotlib
matplotlib.use('Agg')
import matplotlib.pyplot as plt

OUT = Path(__file__).resolve().parents[1] / 'assets' / 'ice-extraction'
OUT.mkdir(parents=True, exist_ok=True)
plt.rcParams.update({'font.size': 10, 'axes.spines.top': False,
    'axes.spines.right': False, 'svg.fonttype': 'none', 'svg.hashsalt': 'extraction-scenarios-v1'})
COLORS = ['#3159b8', '#bd571b', '#23846e', '#8b529c']
# Capacity applies from the given time onward; depletion remains continuous.
MEDIUM = [(0, 1), (.25, 2), (.5, 4), (.75, 8)]
PROFILES = {
    'Slow expansion': [(0, 1), (.25, 1.5), (.5, 2), (.75, 3)],
    'Medium expansion': MEDIUM,
    'Fast expansion': [(0, 1), (.25, 3), (.5, 8), (.75, 20)],
    'Stop expanding at 1/3': [(0, 1), (.25, 2)],
}
X = [i / 600 for i in range(601)]


def exposure(profile, x):
    """H(x) = exact integral of the piecewise constant dimensionless capacity."""
    total = 0
    for i, (start, m) in enumerate(profile):
        end = profile[i+1][0] if i+1 < len(profile) else x
        total += m * max(0, min(end, x)-start)
    return total


def state(profile, alpha, x):
    m = next(m for start, m in reversed(profile) if x >= start)
    d = math.exp(-alpha * exposure(profile, x))
    return m, d, m*d


def section_density(profile, alpha, x, split=False):
    """Independent product of simple section updates, optionally subdivided."""
    d = 1
    for i, (start, m) in enumerate(profile):
        end = profile[i+1][0] if i+1 < len(profile) else x
        duration = max(0, min(end, x)-start)
        if split:
            d *= math.exp(-alpha*m*duration*.37)
            d *= math.exp(-alpha*m*duration*.63)
        else:
            d *= math.exp(-alpha*m*duration)
    return d


def round_axis(ax):
    ax.set(xlim=(0, 1), xlabel='Elapsed fraction of round t/T')
    ax.set_xticks([0, 1/3, 2/3, 1], ['Start', '1/3', '2/3', 'End'])
    for x in [1/3, 2/3]:
        ax.axvline(x, color='#777777', linestyle=':', alpha=.7)
    ax.grid(axis='y', alpha=.2)


def finish(fig, name):
    fig.savefig(OUT / (name+'.svg'), bbox_inches='tight', metadata={'Date': None})
    svg = OUT / (name+'.svg')
    svg.write_text('\n'.join(line.rstrip() for line in svg.read_text().splitlines()) + '\n')
    fig.savefig(OUT / (name+'.png'), dpi=140, bbox_inches='tight')
    plt.close(fig)


fig, axes = plt.subplots(3, 1, figsize=(10, 10), constrained_layout=True)
fig.suptitle('Deposit size: from negligible yield loss to early scarcity\nSame expansion m = 1 / 2 / 4 / 8 by quarter (hypothetical values)')
for alpha, label, color in zip([.001, .5, 4],
    ['Large deposit (α = 0.001)', 'Ice example (α = 0.5)', 'Small deposit (α = 4)'], COLORS):
    states = [state(MEDIUM, alpha, x) for x in X]
    axes[0].plot(X, [100*d for m,d,q in states], label=label, color=color, linewidth=2)
    axes[1].plot(X, [q for m,d,q in states], label=label, color=color, linewidth=2)
axes[1].step([s for s,m in MEDIUM]+[1], [m for s,m in MEDIUM]+[8], where='post', linestyle='--', color='#777777', label='Without density loss: q/P₀ = m')
axes[2].plot(X, [100*(1-state(MEDIUM, .001, x)[1]) for x in X], color=COLORS[0], linewidth=2)
axes[0].set(title='Relative density / efficiency of existing mines', ylabel='d [%]', ylim=(0, 103))
axes[1].set(title='Actual extraction relative to initial capacity P₀', ylabel='q/P₀', ylim=(0, 8.5))
axes[2].set(title='Large deposit detail: efficiency loss stays below 0.4 percentage points', ylabel='Efficiency loss [percentage points]', ylim=(0, .4))
for ax in axes:
    round_axis(ax)
for ax in axes[:2]:
    ax.legend(loc='best', fontsize=9)
finish(fig, 'round-extremes')

fig, axes = plt.subplots(3, 1, figsize=(10, 10), constrained_layout=True)
fig.suptitle('Same deposit, different rates of expansion\nα = 0.5, with hypothetical expansion plans')
for (label, profile), color in zip(PROFILES.items(), COLORS):
    vals = [state(profile, .5, x) for x in X]
    axes[0].step([s for s,m in profile]+[1], [m for s,m in profile]+[profile[-1][1]], where='post', label=label, color=color, linewidth=2)
    axes[1].plot(X, [100*d for m,d,q in vals], label=label, color=color, linewidth=2)
    axes[2].plot(X, [q for m,d,q in vals], label=label, color=color, linewidth=2)
axes[0].set(title='Extraction capacity at full reference yield', ylabel='P/P₀', ylim=(0,21))
axes[1].set(title='Faster expansion accelerates density loss', ylabel='d [%]', ylim=(0,103))
axes[2].set(title='Higher capacity can yield a lower late-round extraction rate', ylabel='q/P₀', ylim=(0,6))
for ax in axes:
    round_axis(ax)
    ax.legend(loc='best', fontsize=9, ncol=2)
finish(fig, 'round-growth')

# Cumulative extraction: gross source yield, independent of storage and consumption.
EXAMPLE_SOURCE = 864000  # Illustrative S, not a game-balancing default.
fig, ax = plt.subplots(figsize=(10, 6), constrained_layout=True)
fig.suptitle('Total extraction since the start of the round\nSame deposit and expansion plans as above, with α = 0.5 and R₀ = S')
for (label, profile), color in zip(PROFILES.items(), COLORS):
    cumulative = [-math.expm1(-.5 * exposure(profile, x)) for x in X]
    ax.plot(X, [100*a for a in cumulative], label=label, color=color, linewidth=2.5)
    ax.scatter([1], [100*cumulative[-1]], color=color, s=25, zorder=3, clip_on=False)
ax.set(title='Cumulative extraction keeps growing as the extraction rate falls',
       ylabel='Extracted share of initial deposit [%]', ylim=(0, 102))
round_axis(ax)
ax.set_yticks([0, 20, 40, 60, 80, 100])
right = ax.secondary_yaxis('right', functions=(
    lambda pct: pct * EXAMPLE_SOURCE / 100000,
    lambda thousands: thousands * 100000 / EXAMPLE_SOURCE))
right.set_ylabel('Quantity [thousand units] for S = 864,000')
ax.legend(loc='upper left', fontsize=9)
finish(fig, 'round-cumulative')

fig, axes = plt.subplots(2, 1, figsize=(10, 8), constrained_layout=True)
fig.suptitle('Idealized compensation: keeping throughput at q = P₀\nContinuously adjustable capacity, with no build time or minimum yield')
for alpha, color in zip([.3, .9, 1.2], COLORS):
    valid_x = [x for x in X if 1-alpha*x > 0]
    label = f'α = {alpha:g}'
    axes[0].plot(valid_x, [100*(1-alpha*x) for x in valid_x], label=label, color=color, linewidth=2)
    axes[1].plot(valid_x, [1/(1-alpha*x) for x in valid_x], label=label, color=color, linewidth=2)
axes[0].set(title='Remaining deposit falls linearly at constant throughput', ylabel='d [%]', ylim=(0,103))
axes[1].set(title='Required capacity rises increasingly fast (shown up to 12×)', ylabel='Required P/P₀', ylim=(0,12))
for ax in axes:
    ax.axvline(1/1.2, color=COLORS[2], linestyle='--', label='Depletion at 5/6 for α = 1.2')
    round_axis(ax)
    ax.legend(loc='best', fontsize=9)
finish(fig, 'round-compensation')

# Mathematical consistency checks for changing capacities and continuous modifiers.
for profile in PROFILES.values():
    for alpha in [.001, .5, 4]:
        for x in [0, .25, 1/3, .5, 2/3, .75, 1]:
            m,d,q = state(profile, alpha, x)
            assert math.isclose(d, section_density(profile, alpha, x), rel_tol=1e-13)
            assert math.isclose(d, section_density(profile, alpha, x, True), rel_tol=1e-13)
            assert math.isclose(q, m*d, rel_tol=1e-13)
            assert 0 < d <= 1
# With P(t)=P0*(1+beta*t/T), the exact exposure is P0*T*(x+beta*x*x/2).
# Trapezoidal integration is exact for a linear function.
for x in [.1, .5, 1]:
    beta = 2
    integral = x*(1+(1+beta*x))/2
    assert math.isclose(integral, x+beta*x*x/2, rel_tol=1e-14)
# Compensating capacity satisfies d'=-alpha*m*d=-alpha up to depletion.
for alpha in [.3, .9, 1.2]:
    for x in [0, .3, .6]:
        d = 1-alpha*x
        m = 1/d
        assert math.isclose(alpha*m*d, alpha, rel_tol=1e-14)
print('Scenarios: d at 1/3, 2/3, and end, with q/P0 at end')
for alpha in [.001, .5, 4]:
    print('alpha', alpha, [round(state(MEDIUM, alpha, x)[1], 9) for x in [1/3, 2/3, 1]], round(state(MEDIUM, alpha, 1)[2], 9))
for label, profile in PROFILES.items():
    print(label, [round(state(profile, .5, x)[1], 9) for x in [1/3, 2/3, 1]], round(state(profile, .5, 1)[2], 9))
print('Verified integral form, interval products, subdivision, and compensation.')

# Compare cumulative source yield to summed exact per-section extraction.
print('Cumulative extraction: share of S at 1/3, 2/3, and end, with final quantity for S=864000')
for label, profile in PROFILES.items():
    last = -1
    for x in X:
        cumulative = -math.expm1(-.5 * exposure(profile, x))
        assert last <= cumulative <= 1
        last = cumulative
        remaining, total = 1.0, 0.0
        for i, (start, m) in enumerate(profile):
            end = profile[i+1][0] if i+1 < len(profile) else x
            duration = max(0, min(end, x)-start)
            amount = -remaining * math.expm1(-.5*m*duration)
            total += amount
            remaining -= amount
        assert math.isclose(cumulative, total, rel_tol=1e-12, abs_tol=1e-14)
        assert math.isclose(remaining+total, 1, abs_tol=1e-14)
    amounts = [-math.expm1(-.5*exposure(profile, x)) for x in [1/3, 2/3, 1]]
    print(label, [round(100*a, 6) for a in amounts], round(EXAMPLE_SOURCE*amounts[-1], 2))
print('Verified cumulative extraction against interval totals, monotonicity, and material balance.')
