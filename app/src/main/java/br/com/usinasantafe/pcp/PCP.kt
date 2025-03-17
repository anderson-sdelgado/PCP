package br.com.usinasantafe.pcp

import android.app.Application
import androidx.work.Configuration
import br.com.usinasantafe.pcp.di.apiRetrofitModule
import br.com.usinasantafe.pcp.di.apiRoomModule
import br.com.usinasantafe.pcp.di.datasourceRetrofitModule
import br.com.usinasantafe.pcp.di.datasourceRoomModule
import br.com.usinasantafe.pcp.di.datasourceSharedPreferencesModule
import br.com.usinasantafe.pcp.di.retrofitModule
import br.com.usinasantafe.pcp.di.repositoryModule
import br.com.usinasantafe.pcp.di.roomModule
import br.com.usinasantafe.pcp.di.sharedPreferencesModule
import br.com.usinasantafe.pcp.di.usecaseBackgroundModule
import br.com.usinasantafe.pcp.di.usecaseChaveEquipModule
import br.com.usinasantafe.pcp.di.usecaseChaveModule
import br.com.usinasantafe.pcp.di.usecaseCleanTableModule
import br.com.usinasantafe.pcp.di.usecaseCommonModule
import br.com.usinasantafe.pcp.di.usecaseConfigModule
import br.com.usinasantafe.pcp.di.usecaseInitialModule
import br.com.usinasantafe.pcp.di.usecaseProprioModule
import br.com.usinasantafe.pcp.di.usecaseRecoverServerModule
import br.com.usinasantafe.pcp.di.usecaseResidenciaModule
import br.com.usinasantafe.pcp.di.usecaseUpdateModule
import br.com.usinasantafe.pcp.di.usecaseSaveAllTableModule
import br.com.usinasantafe.pcp.di.usecaseVisitTercModule
import br.com.usinasantafe.pcp.di.viewModelChaveEquipModule
import br.com.usinasantafe.pcp.di.viewModelChaveModule
import br.com.usinasantafe.pcp.di.viewModelConfigModule
import br.com.usinasantafe.pcp.di.viewModelInicialModule
import br.com.usinasantafe.pcp.di.viewModelProprioModule
import br.com.usinasantafe.pcp.di.viewModelResidenciaModule
import br.com.usinasantafe.pcp.di.viewModelSplashModule
import br.com.usinasantafe.pcp.di.viewModelVisitTercModule
import br.com.usinasantafe.pcp.di.workManagerModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin

class PCP : Application(), KoinComponent, Configuration.Provider {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@PCP)
            workManagerFactory()
            modules(
                listOf(
                    sharedPreferencesModule,
                    retrofitModule,
                    roomModule,
                ) + commonModuleList
            )
        }
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.INFO)
            .build()
}

val commonModuleList = listOf(
    viewModelConfigModule,
    viewModelInicialModule,
    viewModelSplashModule,
    viewModelChaveModule,
    viewModelChaveEquipModule,
    viewModelProprioModule,
    viewModelResidenciaModule,
    viewModelVisitTercModule,
    usecaseBackgroundModule,
    usecaseChaveModule,
    usecaseChaveEquipModule,
    usecaseCleanTableModule,
    usecaseCommonModule,
    usecaseConfigModule,
    usecaseInitialModule,
    usecaseProprioModule,
    usecaseVisitTercModule,
    usecaseResidenciaModule,
    usecaseUpdateModule,
    usecaseRecoverServerModule,
    usecaseSaveAllTableModule,
    repositoryModule,
    datasourceSharedPreferencesModule,
    datasourceRoomModule,
    datasourceRetrofitModule,
    apiRetrofitModule,
    apiRoomModule,
    workManagerModule,
)
