package de.bwoester.coldfrontier.progress;

import de.bwoester.coldfrontier.EventLogStub;
import de.bwoester.coldfrontier.buildings.Building;
import de.bwoester.coldfrontier.buildings.BuildingDataProvider;
import de.bwoester.coldfrontier.buildings.BuildingMsg;
import de.bwoester.coldfrontier.messaging.GameEventLog;
import de.bwoester.coldfrontier.messaging.GameEventSubject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProgressServiceTest {

    @Mock
    private BuildingDataProvider buildingDataProvider;
    
    @Mock
    private Building building;
    
    @Mock
    private BuildingMsg buildingMsg;

    private GameEventLog<ProgressMsg> progressLog;
    private ProgressService progressService;
    
    @BeforeEach
    void setUp() {
        EventLogStub eventLogStub = new EventLogStub();
        progressLog = eventLogStub.inMemoryGameEventLog
                .viewOfType(ProgressMsg.class, GameEventSubject.Progress.building("planet-1"));
        progressService = new ProgressService(progressLog, buildingDataProvider);
    }
    
    @Test
    void startBuilding_ShouldAddProgressMessage() {
        // When
        progressService.startBuilding(building, 1.0);
        
        // Then
        ProgressMsg latest = progressLog.getLatest();
        assertNotNull(latest);
        assertInstanceOf(CreateBuildingProgressMsg.class, latest);
        
        CreateBuildingProgressMsg msg = (CreateBuildingProgressMsg) latest;
        assertSame(building, msg.building());
        assertEquals(1.0, msg.timeToBuildMultiplier());
        assertEquals(0.0, msg.progress());
        assertFalse(msg.consumed());
    }
    
    @Test
    void hasBuildingInProgress_WithNoBuilding_ShouldReturnFalse() {
        // When & Then
        assertFalse(progressService.hasBuildingInProgress());
    }
    
    @Test
    void hasBuildingInProgress_WithBuildingInProgress_ShouldReturnTrue() {
        // Given
        progressService.startBuilding(building, 1.0);
        
        // When & Then
        assertTrue(progressService.hasBuildingInProgress());
    }
    
    @Test
    void hasBuildingInProgress_WithCompletedBuilding_ShouldReturnFalse() {
        // Given
        progressLog.add(new CreateBuildingProgressMsg(building, 1.0, 1.0, false));
        
        // When & Then
        assertFalse(progressService.hasBuildingInProgress());
    }
    
    @Test
    void increaseProgress_WithNoBuilding_ShouldDoNothing() {
        // When
        progressService.increaseProgress();
        
        // Then
        assertTrue(progressLog.isEmpty());
    }
    
    @Test
    void increaseProgress_WithBuildingInProgress_ShouldIncrementProgress() {
        // Given
        progressService.startBuilding(building, 1.0);
        
        // When
        when(buildingDataProvider.getData(building)).thenReturn(buildingMsg);
        when(buildingMsg.ticksToBuild()).thenReturn(10L);
        progressService.increaseProgress();
        
        // Then
        ProgressMsg latest = progressLog.getLatest();
        assertEquals(0.1, latest.progress()); // Exact 1/10 progress increment
    }
    
    @Test
    void increaseProgress_WithTimeToBuildMultiplier_ShouldAdjustIncrement() {
        // Given
        progressService.startBuilding(building, 2.0); // Double the build time
        
        // When
        when(buildingDataProvider.getData(building)).thenReturn(buildingMsg);
        when(buildingMsg.ticksToBuild()).thenReturn(10L);
        progressService.increaseProgress();
        
        // Then
        ProgressMsg latest = progressLog.getLatest();
        assertEquals(0.05, latest.progress()); // Exact 1/20 progress increment
    }
    
    @Test
    void increaseProgress_WithCompletedButNotConsumedBuilding_ShouldNotIncreaseProgress() {
        // Given
        progressLog.add(new CreateBuildingProgressMsg(building, 1.0, 1.0, false));
        int messageCount = progressLog.getAll().size();
        
        // When
        progressService.increaseProgress();
        
        // Then
        assertEquals(messageCount, progressLog.getAll().size()); // No new message added
        assertEquals(1.0, progressLog.getLatest().progress()); // Progress remains at 1.0
    }
    
    @Test
    void increaseProgress_WithCompletedAndConsumedBuilding_ShouldDoNothing() {
        // Given
        progressLog.add(new CreateBuildingProgressMsg(building, 1.0, 1.0, true));
        int messageCount = progressLog.getAll().size();
        
        // When
        progressService.increaseProgress();
        
        // Then
        assertEquals(messageCount, progressLog.getAll().size()); // No new message added
    }
    
    @Test
    void pollCompletedBuilding_WithNoBuilding_ShouldReturnEmpty() {
        // When
        Optional<Building> result = progressService.pollCompletedBuilding();
        
        // Then
        assertTrue(result.isEmpty());
    }
    
    @Test
    void pollCompletedBuilding_WithIncompleteBuilding_ShouldReturnEmpty() {
        // Given
        progressService.startBuilding(building, 1.0);
        
        // When
        Optional<Building> result = progressService.pollCompletedBuilding();
        
        // Then
        assertTrue(result.isEmpty());
    }
    
    @Test
    void pollCompletedBuilding_WithCompletedBuilding_ShouldReturnAndMarkConsumed() {
        // Given
        progressLog.add(new CreateBuildingProgressMsg(building, 1.0, 1.0, false));
        
        // When
        Optional<Building> result = progressService.pollCompletedBuilding();
        
        // Then
        assertTrue(result.isPresent());
        assertSame(building, result.get());
        
        // The building should be marked as consumed
        ProgressMsg latest = progressLog.getLatest();
        assertTrue(latest.consumed());
    }
    
    @Test
    void pollCompletedBuilding_WithAlreadyConsumedBuilding_ShouldReturnEmpty() {
        // Given
        progressLog.add(new CreateBuildingProgressMsg(building, 1.0, 1.0, true));
        
        // When
        Optional<Building> result = progressService.pollCompletedBuilding();
        
        // Then
        assertTrue(result.isEmpty());
    }
    
    @Test
    void completeScenario_StartBuildAndCompleteBuilding() {
        // Given
        progressService.startBuilding(building, 1.0);
        
        // Process 9 ticks (should reach 90% progress)
        when(buildingDataProvider.getData(building)).thenReturn(buildingMsg);
        when(buildingMsg.ticksToBuild()).thenReturn(10L);
        for (int i = 0; i < 9; i++) {
            progressService.increaseProgress();
        }
        
        // Verify progress at 90%
        assertEquals(0.9, progressLog.getLatest().progress());
        assertTrue(progressService.hasBuildingInProgress());
        
        // Final tick to complete building
        progressService.increaseProgress();
        
        // Verify building is complete but not consumed
        assertEquals(1.0, progressLog.getLatest().progress());
        assertFalse(progressService.hasBuildingInProgress());
        assertFalse(progressLog.getLatest().consumed());
        
        // Poll the completed building
        Optional<Building> completed = progressService.pollCompletedBuilding();
        
        // Verify building is returned and marked as consumed
        assertTrue(completed.isPresent());
        assertSame(building, completed.get());
        assertTrue(progressLog.getLatest().consumed());
        
        // Further polls should return empty
        assertTrue(progressService.pollCompletedBuilding().isEmpty());
    }

}
