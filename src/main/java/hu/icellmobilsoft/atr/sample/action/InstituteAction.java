package hu.icellmobilsoft.atr.sample.action;

import javax.inject.Inject;

import hu.icellmobilsoft.atr.sample.converter.InstituteConverter;
import hu.icellmobilsoft.atr.sample.repository.InstituteRepository;

public class InstituteAction {

    @Inject
    private InstituteRepository instituteRepository;

    @Inject
    InstituteConverter instituteConverter;


}
