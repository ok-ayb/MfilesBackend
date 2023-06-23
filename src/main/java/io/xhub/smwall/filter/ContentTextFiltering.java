package io.xhub.smwall.filter;

import com.nimbusds.jose.shaded.gson.Gson;
import io.xhub.smwall.domains.Media;
import io.xhub.smwall.enumeration.LanguageText;
import io.xhub.smwall.filter.responses.RequestedAttributesResponse;
import io.xhub.smwall.filter.responses.TextFilteringResponse;
import io.xhub.smwall.filter.responses.ToxicityResponse;
import io.xhub.smwall.utlis.PerspectiveApiUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Stream;

@Slf4j
@AllArgsConstructor
@Component
public class ContentTextFiltering {
    private final PerspectiveApiUtils perspectiveApiUtils;

    public void textFiltering(Media media) {


        if (media.getText() != null && !media.getText().isEmpty()) {
            log.info("Start filtering");
            TextFilteringResponse analysisResponse = perspectiveApiUtils.analyzeComment(media.getText());


            if (analysisResponse != null) {

                System.out.println(new Gson().toJson(analysisResponse));

                List<String> languagesNode = analysisResponse.getDetectedLanguages();
                RequestedAttributesResponse attributeScores = analysisResponse.getAttributeScores();
                ToxicityResponse toxicityScore = analysisResponse.getAttributeScores().getToxicity();


                List<String> languages = Stream.of(LanguageText.values())
                        .map(Enum::name)
                        .toList();

                boolean isLanguageRespected = languagesNode != null && languagesNode.stream().anyMatch(languages::contains);

                boolean isToxicityBelowThreshold = toxicityScore != null && attributeScores.getToxicity().getSummaryScore().getValue() < 0.5;


                log.info("response ");

                if (isLanguageRespected && isToxicityBelowThreshold) {
                    media.setClean(Boolean.TRUE);
                    log.info("Media is clean ");
                } else {
                    media.setClean(Boolean.FALSE);
                    media.setHidden(Boolean.TRUE);
                    log.info("Media is not clean");
                }
            }
        } else {
            log.info("media Text is empty ");
            media.setClean(Boolean.TRUE);
        }
    }


}
