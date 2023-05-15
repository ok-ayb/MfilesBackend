package io.xhub.smwall.mappers;

import io.xhub.smwall.domains.Sponsor;
import io.xhub.smwall.dto.SponsorDTO;
import org.springframework.stereotype.Component;

@Component
public class SponsorMapper {
    public SponsorDTO toDTO(Sponsor sponsor) {
        if (sponsor == null) return null;
        SponsorDTO sponsorDTO = new SponsorDTO();
        sponsorDTO.setType(sponsor.getType());
        sponsorDTO.setLogoUrl(sponsor.getLogoUrl());
        return sponsorDTO;
    }
}
