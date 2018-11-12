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

import com.piestack.adventelegraph.ui.PostDetailActivity
import com.piestack.adventelegraph.ui.bible.Bible
import com.piestack.adventelegraph.ui.bible.dialog.TabbedDialog
import com.piestack.adventelegraph.ui.bible.dialog.fragment.FragmentChapter
import com.piestack.adventelegraph.ui.bible.dialog.fragment.FragmentVerse
import com.piestack.adventelegraph.ui.bible.fragment.BibleFragment
import com.piestack.adventelegraph.ui.hymnal.Hymnal
import com.piestack.adventelegraph.ui.main.MainActivity
import com.piestack.adventelegraph.ui.main.fragments.*
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class ActivityBuilder {

    @ContributesAndroidInjector
    abstract fun bindHomeActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun bindBibleActivity(): Bible

    @ContributesAndroidInjector
    abstract fun bindHymnalActivity(): Hymnal

    @ContributesAndroidInjector
    abstract fun bindPostDetailActivity(): PostDetailActivity

    @ContributesAndroidInjector
    abstract fun bindBibleFragment(): BibleFragment

    @ContributesAndroidInjector
    abstract fun bindBibleTabbedDialog(): TabbedDialog

    @ContributesAndroidInjector
    abstract fun bindBibleTabFragmentChapter(): FragmentChapter

    @ContributesAndroidInjector
    abstract fun bindBibleTabFragmentVerse(): FragmentVerse

    @ContributesAndroidInjector
    abstract fun bindNavigationFragment(): BooksFragment

    @ContributesAndroidInjector
    abstract fun bindHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun bindMusicFragment(): MusicFragment

    @ContributesAndroidInjector
    abstract fun bindProphecyFragment(): ProphecyFragment

    @ContributesAndroidInjector
    abstract fun bindVideoFragment(): VideoFragment

}