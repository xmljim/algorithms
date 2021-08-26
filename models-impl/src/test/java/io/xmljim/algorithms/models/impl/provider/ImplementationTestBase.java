package io.xmljim.algorithms.models.impl.provider;

import io.xmljim.algorithms.model.provider.ModelProvider;

import java.util.ServiceLoader;

public class ImplementationTestBase {

    private ModelProvider modelProvider;

    ModelProvider getModelProvider() {
        if (modelProvider == null) {
            Iterable<ModelProvider> modelProviders = ServiceLoader.load(ModelProvider.class);
            modelProvider = modelProviders.iterator().next();
        }

        return modelProvider;
    }
}
