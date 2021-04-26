package ee.taltech.varadehaldamine.controller;


import ee.taltech.varadehaldamine.model.Asset;
import ee.taltech.varadehaldamine.model.Person;
import ee.taltech.varadehaldamine.modelDTO.AssetInfo;
import ee.taltech.varadehaldamine.modelDTO.AssetInfoShort;
import ee.taltech.varadehaldamine.service.AssetService;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientId;
import org.springframework.web.bind.annotation.*;
import javax.persistence.*;
import javax.transaction.Transactional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.security.Principal;
import java.util.List;

@RequestMapping("asset")
@RestController
public class AssetController {


    @PreAuthorize("hasRole('ROLE_admin')")
    @GetMapping("/account")
    public Object getAccount() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }


    @Autowired
    AssetService assetService;

    @GetMapping("/filtered")
    @ResponseBody
    public ResponseEntity<Page<AssetInfoShort>> getAssets(
            AssetInfoShort assetSearchCriteria,
            @RequestParam(required = false, value = "page", defaultValue = "0") int page,
            // Using default value of 10 instead of a pathVariable
            @RequestParam(required = false, value = "size", defaultValue = "10") int size,
            @RequestParam(value = "order", required = false, defaultValue = "ASC") String order,
            @RequestParam(value = "sortBy", required = false, defaultValue = "id") String sortBy) {
        return new ResponseEntity<>(assetService.getAssetsList(page, size, assetSearchCriteria, order, sortBy), HttpStatus.OK);
    }

    @Transactional
    @GetMapping("/audit/{id}")
    public Page<AssetInfo> getAuditById(@PathVariable String id) {
        return assetService.getAuditById(id);
    }

    @GetMapping
    public List<AssetInfoShort> getAll() {
        return assetService.findAll();
    }

    @GetMapping("/{id}")
    public AssetInfo getAssetById(@PathVariable String id) {
        return assetService.getAssetById(id);
    }

    @PutMapping("/{id}")
    public Asset updateAsset(@RequestBody AssetInfo assetInfo, @PathVariable String id) {
        return assetService.update(assetInfo, id);
    }


    @PostMapping
    public ResponseEntity<Object> addAsset(@RequestBody AssetInfo asset) {
        if (assetService.addAsset(asset) != null) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
    }
}
