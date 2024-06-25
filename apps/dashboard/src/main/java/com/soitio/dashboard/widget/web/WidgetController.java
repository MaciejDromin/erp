package com.soitio.dashboard.widget.web;

import com.soitio.dashboard.widget.application.WidgetRepository;
import com.soitio.dashboard.widget.domain.dto.WidgetCreationDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WidgetController {

    // TODO: Implement this class

    private final WidgetRepository widgetRepository;

    public void createWidget(WidgetCreationDto widgetCreation) {
        widgetRepository.createWidget(widgetCreation);
    }

}
