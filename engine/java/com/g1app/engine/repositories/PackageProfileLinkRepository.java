package com.g1app.engine.repositories;

import com.g1app.engine.models.PackageProfileLink;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PackageProfileLinkRepository extends JpaRepository<PackageProfileLink, UUID> {
    List<PackageProfileLink> findByPackageIdAndIsActiveAndIsDeleted(UUID pkgId, boolean valActive, boolean valDeleted);
}
