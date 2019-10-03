package com.le.app.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.le.app.model.IrregularVerb;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class IrregularVerbDto {
    private Long id;
    private String infinitive;
    private String transcriptionInfinitive;
    private String pastTense;
    private String transcriptionPastTense;
    private String pastParticiple;
    private String transcriptionPastParticiple;
    private String translation;
    public boolean isLearnt = false;

    public IrregularVerb toIrregularVerb() {
        IrregularVerb irregularVerb = new IrregularVerb();
        irregularVerb.setId(id);
        irregularVerb.setInfinitive(infinitive);
        irregularVerb.setTranscriptionInfinitive(transcriptionInfinitive);
        irregularVerb.setPastTense(pastTense);
        irregularVerb.setTranscriptionPastTense(transcriptionPastTense);
        irregularVerb.setPastParticiple(pastParticiple);
        irregularVerb.setTranscriptionPastParticiple(transcriptionPastParticiple);
        irregularVerb.setTranslation(translation);
        return irregularVerb;
    }

    public static IrregularVerbDto fromIrregularVerb(IrregularVerb irregularVerb) {

        IrregularVerbDto irregularVerbDto = new IrregularVerbDto();

        irregularVerbDto.setId(irregularVerb.getId());
        irregularVerbDto.setInfinitive(irregularVerb.getInfinitive());
        irregularVerbDto.setTranscriptionInfinitive(irregularVerb.getTranscriptionInfinitive());
        irregularVerbDto.setPastTense(irregularVerb.getPastTense());
        irregularVerbDto.setTranscriptionPastTense(irregularVerb.getTranscriptionPastTense());
        irregularVerbDto.setPastParticiple(irregularVerb.getPastParticiple());
        irregularVerbDto.setTranscriptionPastParticiple(irregularVerb.getTranscriptionPastParticiple());
        irregularVerbDto.setTranslation(irregularVerb.getTranslation());
        return irregularVerbDto;
    }
}
