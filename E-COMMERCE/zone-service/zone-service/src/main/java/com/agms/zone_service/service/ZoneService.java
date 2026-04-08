package com.agms.zone_service.service;

import com.agms.zone_service.IOTClient.ExternalApiClient;
import com.agms.zone_service.IOTClient.ExternalTokenManager;
import com.agms.zone_service.entity.Zone;
import com.agms.zone_service.repository.ZoneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ZoneService {
    private final ZoneRepository zoneRepository;
    private final ExternalApiClient externalApiClient;
    private final ExternalTokenManager tokenManager;

    public Zone createZone(Zone zone) {
        String token = tokenManager.getValidToken();
        ExternalApiClient.DeviceResponse resp = externalApiClient.registerDevice(
                "Bearer " + token,
                new ExternalApiClient.DeviceRequest(zone.getName(), zone.getName().replace(" ", "-"))
        );
        zone.setDeviceId(resp.deviceId());
        return zoneRepository.save(zone);
    }

    public Zone getZone(Long id) { return zoneRepository.findById(id).orElseThrow(); }
    public Zone updateZone(Long id, Zone zone) { zone.setId(id); return zoneRepository.save(zone); }
    public void deleteZone(Long id) { zoneRepository.deleteById(id); }
    public List<Zone> getAllZones() { return zoneRepository.findAll(); }
}
