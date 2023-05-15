package io.xhub.smwall.mappers;

import io.xhub.smwall.domains.WallFooter;
import io.xhub.smwall.dto.SponsorDTO;
import io.xhub.smwall.dto.WallFooterDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class WallFooterMapper {

    private final SponsorMapper sponsorMapper;

    public WallFooterDTO toDTO(WallFooter wallFooter) {
        if (wallFooter == null) return null;
        WallFooterDTO wallFooterDTO = new WallFooterDTO();

        wallFooterDTO.setCoOrganizer(wallFooter.getCoOrganizer());
        wallFooterDTO.setInstitutionalPartners(wallFooter.getInstitutionalPartners());
        Map<String, List<String>> sponsors = wallFooter.getSponsors().stream()
                .map(sponsorMapper::toDTO)
                .filter(sponsor -> sponsor.getType() != null && sponsor.getLogoUrl() != null)
                .collect(Collectors.groupingBy(SponsorDTO::getType,
                        Collectors.mapping(SponsorDTO::getLogoUrl, Collectors.toList())));
        wallFooterDTO.setSponsors(sponsors);
        wallFooterDTO.setLogoUrl(wallFooter.getLogoUrl());
        return wallFooterDTO;
    }
}
