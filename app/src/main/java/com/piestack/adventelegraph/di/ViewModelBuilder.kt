/*
 * Copyright (c) 2018. Jeffrey Nyauke.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.piestack.adventelegraph.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.piestack.adventelegraph.ui.bible.BibleActivityViewModel
import com.piestack.adventelegraph.ui.hymnal.HymnalActivityViewModel
import com.piestack.adventelegraph.ui.main.MainActivityViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class ViewModelBuilder {

    @Binds
    @IntoMap
    @ViewModelKey(BibleActivityViewModel::class)
    internal abstract fun bindBibleViewModel(bibleActivityViewModel: BibleActivityViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    internal abstract fun bindMainViewModel(mainActivityViewModel: MainActivityViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HymnalActivityViewModel::class)
    internal abstract fun bindHymnalViewModel(hymnalActivityViewModel: HymnalActivityViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFractory(factory: ViewModelFactory): ViewModelProvider.Factory
}