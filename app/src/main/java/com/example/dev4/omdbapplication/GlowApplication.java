package com.example.dev4.omdbapplication;

        import android.app.Application;

        import com.example.dev4.omdbapplication.di.component.ApplicationComponent;
        import com.example.dev4.omdbapplication.di.component.DaggerApplicationComponent;
        import com.example.dev4.omdbapplication.di.component.DaggerRemoteClientComponent;
        import com.example.dev4.omdbapplication.di.component.RemoteClientComponent;
        import com.example.dev4.omdbapplication.di.modules.ApplicationModule;
        import com.example.dev4.omdbapplication.di.modules.RemoteClientModule;

public class GlowApplication extends Application {
    private static GlowApplication app;
    private ApplicationComponent applicationComponent;
    private RemoteClientComponent remoteClientComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        initialize();
    }

    private void initialize() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();

        remoteClientComponent = DaggerRemoteClientComponent.builder()
                .remoteClientModule(new RemoteClientModule(this))
                .build();
    }

    public RemoteClientComponent getRemoteClientComponent() {
        return remoteClientComponent;
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    public static GlowApplication getApp() {
        return app;
    }
}
