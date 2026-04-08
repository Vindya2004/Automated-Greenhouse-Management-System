package com.agms.zone_service.controller;

import com.agms.zone_service.entity.Zone;
import com.agms.zone_service.service.ZoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/zones")
@RequiredArgsConstructor
public class ZoneController {
    private final ZoneService zoneService;

    @PostMapping
    public Zone create(@RequestBody Zone zone) { return zoneService.createZone(zone); }
    @GetMapping("/{id}")
    public Zone get(@PathVariable Long id) { return zoneService.getZone(id); }
    @GetMapping
    public List<Zone> getAll() { return zoneService.getAllZones(); }
    @PutMapping("/{id}")
    public Zone update(@PathVariable Long id, @RequestBody Zone zone) { return zoneService.updateZone(id, zone); }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { zoneService.deleteZone(id); }
}
