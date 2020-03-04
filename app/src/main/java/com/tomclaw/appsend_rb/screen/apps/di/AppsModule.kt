package com.tomclaw.appsend_rb.screen.apps.di

import android.content.Context
import android.os.Bundle
import com.avito.konveyor.ItemBinder
import com.avito.konveyor.adapter.AdapterPresenter
import com.avito.konveyor.adapter.SimpleAdapterPresenter
import com.avito.konveyor.blueprint.ItemBlueprint
import com.tomclaw.appsend_rb.screen.apps.AppEntityConverter
import com.tomclaw.appsend_rb.screen.apps.AppEntityConverterImpl
import com.tomclaw.appsend_rb.screen.apps.AppsInteractor
import com.tomclaw.appsend_rb.screen.apps.AppsInteractorImpl
import com.tomclaw.appsend_rb.screen.apps.AppsPresenter
import com.tomclaw.appsend_rb.screen.apps.AppsPresenterImpl
import com.tomclaw.appsend_rb.screen.apps.PackageManagerWrapper
import com.tomclaw.appsend_rb.screen.apps.PackageManagerWrapperImpl
import com.tomclaw.appsend_rb.screen.apps.adapter.app.AppItemBlueprint
import com.tomclaw.appsend_rb.screen.apps.adapter.app.AppItemPresenter
import com.tomclaw.appsend_rb.util.PerActivity
import com.tomclaw.appsend_rb.util.SchedulersFactory
import dagger.Module
import dagger.Provides
import dagger.Lazy
import dagger.multibindings.IntoSet

@Module
class AppsModule(
        private val context: Context,
        private val state: Bundle?
) {

    @Provides
    @PerActivity
    internal fun provideAdapterPresenter(binder: ItemBinder): AdapterPresenter {
        return SimpleAdapterPresenter(binder, binder)
    }

    @Provides
    @PerActivity
    internal fun providePresenter(
            interactor: AppsInteractor,
            adapterPresenter: Lazy<AdapterPresenter>,
            appEntityConverter: AppEntityConverter,
            schedulers: SchedulersFactory
    ): AppsPresenter = AppsPresenterImpl(interactor, adapterPresenter, appEntityConverter, schedulers, state)

    @Provides
    @PerActivity
    internal fun provideInteractor(
            packageManager: PackageManagerWrapper,
            schedulers: SchedulersFactory
    ): AppsInteractor = AppsInteractorImpl(packageManager, schedulers)

    @Provides
    @PerActivity
    internal fun provideAppInfoConverter(): AppEntityConverter {
        return AppEntityConverterImpl()
    }

    @Provides
    @PerActivity
    internal fun providePackageManagerWrapper(): PackageManagerWrapper {
        return PackageManagerWrapperImpl(context.packageManager)
    }

    @Provides
    @PerActivity
    internal fun provideItemBinder(
            blueprintSet: Set<@JvmSuppressWildcards ItemBlueprint<*, *>>
    ): ItemBinder {
        return ItemBinder.Builder().apply {
            blueprintSet.forEach { registerItem(it) }
        }.build()
    }

    @Provides
    @IntoSet
    @PerActivity
    internal fun provideAppItemBlueprint(
            presenter: AppItemPresenter
    ): ItemBlueprint<*, *> = AppItemBlueprint(presenter)

    @Provides
    @PerActivity
    internal fun provideAppItemPresenter(presenter: AppsPresenter) =
            AppItemPresenter(presenter)

}