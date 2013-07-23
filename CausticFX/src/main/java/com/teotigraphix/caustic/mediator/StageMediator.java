
package com.teotigraphix.caustic.mediator;

import org.androidtransfuse.event.EventObserver;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.teotigraphix.caustic.model.IApplicationModel;
import com.teotigraphix.caustic.model.IApplicationModel.OnApplicationModelDirtyChanged;
import com.teotigraphix.caustic.model.IStageModel;
import com.teotigraphix.caustk.application.ICaustkApplicationProvider;
import com.teotigraphix.caustk.application.core.MediatorBase;

@Singleton
public class StageMediator extends MediatorBase {

    @Inject
    IStageModel stageModel;

    IApplicationModel applicationModel;

    public StageMediator() {
    }

    @Inject
    public StageMediator(ICaustkApplicationProvider provider, IApplicationModel applicationModel) {
        super(provider);
        this.applicationModel = applicationModel;
        applicationModel.getDispatcher().register(OnApplicationModelDirtyChanged.class,
                new EventObserver<OnApplicationModelDirtyChanged>() {
                    @Override
                    public void trigger(OnApplicationModelDirtyChanged object) {
                        onApplicationModelDirtyChangedHandler();
                    }
                });
    }

    protected void onApplicationModelDirtyChangedHandler() {
        stageModel.refreshTitle();
    }
}