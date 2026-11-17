"""Reproduce the continuous ice-extraction examples and charts.

Run from any directory: python doc/game-design/tools/plot_ice_extraction.py
Requires Python 3 and matplotlib. No game/runtime dependency.
"""
from pathlib import Path
import math
import matplotlib
matplotlib.use('Agg')
import matplotlib.pyplot as plt

OUT = Path(__file__).resolve().parents[1] / 'assets' / 'ice-extraction'
OUT.mkdir(parents=True, exist_ok=True)
plt.rcParams.update({'font.size': 11, 'axes.spines.top': False,
                     'axes.spines.right': False, 'svg.fonttype': 'none',
                     'svg.hashsalt': 'ice-extraction-v1'})
COLORS = ['#3159b8', '#bd571b', '#23846e']


def state(s, r0, p, t):
    """s, r0 in ice units; p in ice/h; t in h. Return R, relative density, q, A."""
    assert s > 0 and 0 <= r0 <= s and p >= 0 and t >= 0
    r = r0 * math.exp(-p / s * t)
    a = -r0 * math.expm1(-p / s * t)
    return r, r / s, p * r / s, a


def series(s, r0, p, times, index):
    return [state(s, r0, p, t)[index] for t in times]


def finish(fig, filename):
    svg = OUT / (filename + '.svg')
    fig.savefig(svg, bbox_inches='tight', metadata={'Date': None})
    svg.write_text('\n'.join(line.rstrip() for line in svg.read_text().splitlines()) + '\n')
    fig.savefig(OUT / (filename + '.png'), dpi=150, bbox_inches='tight')
    plt.close(fig)


times = [8 * i / 400 for i in range(401)]
fig, axes = plt.subplots(2, 2, figsize=(11, 7), constrained_layout=True)
fig.suptitle('Eight hours under constant operating conditions\nS = R₀ = 50,000 ice, P = 2,000 ice/h (example values)')
for ax, index, title, unit in zip(axes.flat, [0, 1, 2, 3],
    ['Remaining deposit R', 'Relative density d = R/S', 'Instantaneous rate q', 'Extracted since interval start A'],
    ['ice', '% of reference deposit', 'ice/h', 'ice']):
    vals = series(50000, 50000, 2000, times, index)
    if index == 1:
        vals = [100 * v for v in vals]
    ax.plot(times, vals, color=COLORS[0], linewidth=2.5)
    ax.set(title=title, xlabel='Time since interval start [h]', ylabel=unit, xlim=(0, 8))
    ax.grid(alpha=.2)
    label = f'{vals[-1]:,.2f}'
    ax.annotate(label, (8, vals[-1]), xytext=(-8, -18 if index == 3 else 10), textcoords='offset points', ha='right')
finish(fig, 'eight-hours')

fig, axes = plt.subplots(3, 1, figsize=(10, 10), constrained_layout=True)
fig.suptitle('Effect of changing one model parameter at a time')
for p, color in zip([1000, 2000, 4000], COLORS):
    axes[0].plot(times, [100 * v for v in series(50000, 50000, p, times, 1)], label=f'P = {p:,} ice/h', color=color, linewidth=2)
axes[0].set(title='Higher capacity P reduces density faster (S = R₀ = 50,000 ice)', ylabel='Relative density [%]')
for s, color in zip([25000, 50000, 100000], COLORS):
    axes[1].plot(times, [100 * v for v in series(s, s, 2000, times, 1)], label=f'S = R₀ = {s:,} ice', color=color, linewidth=2)
axes[1].set(title='Larger reference deposit S slows density loss (P = 2,000 ice/h)', ylabel='Relative density [%]')
for d0, color in zip([1, .6, .3], COLORS):
    axes[2].plot(times, series(50000, 50000*d0, 2000, times, 2), label=f'R₀/S = {d0:.0%}', color=color, linewidth=2)
axes[2].set(title='Lower R₀ reduces the initial rate (S = 50,000 ice, P = 2,000 ice/h)', ylabel='Extraction rate [ice/h]')
for ax in axes:
    ax.set(xlabel='Time since interval start [h]', xlim=(0, 8))
    ax.legend(loc='best', ncol=3)
    ax.grid(alpha=.2)
finish(fig, 'parameters')

long_times = [16*i/800 for i in range(801)]
r8 = state(50000, 50000, 2000, 8)[0]
fig, axes = plt.subplots(3, 1, figsize=(10, 9), constrained_layout=True)
fig.suptitle('Mine completed after eight hours: P rises from 2,000 to 4,000 ice/h\nAssumes no shortages and that the mine contributes immediately after completion')
for ax, index, title, unit in zip(axes, [0, 2, 3],
    ['Remaining deposit stays continuous', 'Extraction rate jumps at completion', 'Cumulative extraction stays continuous'], ['ice', 'ice/h', 'ice']):
    vals = []
    for t in long_times:
        if t < 8:
            val = state(50000, 50000, 2000, t)[index]
        else:
            val = state(50000, r8, 4000, t-8)[index]
            if index == 3:
                val += 50000-r8
        vals.append(val)
    ax.plot(long_times, vals, color=COLORS[0], linewidth=2)
    if index == 2:
        before = state(50000, 50000, 2000, 8)[2]
        ax.plot([8, 8], [before, 2*before], color=COLORS[0], linewidth=2)
    ax.axvline(8, color=COLORS[1], linestyle='--', label='Construction completes at 8 h')
    ax.set(title=title, ylabel=unit, xlabel='Elapsed game time [h]', xlim=(0,16))
    ax.legend(loc='best')
    ax.grid(alpha=.2)
finish(fig, 'construction-event')

# Check the mathematical examples, balance and subdivision independence.
for p in [0, 1000, 2000, 4000]:
    r, d, q, a = state(50000, 50000, p, 8)
    assert math.isclose(r+a, 50000, abs_tol=1e-8)
    split = state(50000, state(50000, 50000, p, 3)[0], p, 5)[0]
    assert math.isclose(r, split, rel_tol=1e-14)
    per_second = 50000
    for _ in range(28800):
        per_second = state(50000, per_second, p, 1/3600)[0]
    assert math.isclose(r, per_second, rel_tol=1e-10)
assert state(50000, 0, 2000, 8) == (0, 0, 0, 0)
print('Examples: t [h], R [ice], d, q [ice/h], A [ice]')
for t in [0, 1, 4, 8, 12]:
    print(t, *(f'{v:.9f}' for v in state(50000, 50000, 2000, t)))
print('After construction completion, t=16 h:', state(50000, r8, 4000, 8))
print('Verified balance, edge cases, and subdivision independence. Generated charts.')
