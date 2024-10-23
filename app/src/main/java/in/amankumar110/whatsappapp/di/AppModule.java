package in.amankumar110.whatsappapp.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import in.amankumar110.whatsappapp.repositories.UserRepository;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule {


}
