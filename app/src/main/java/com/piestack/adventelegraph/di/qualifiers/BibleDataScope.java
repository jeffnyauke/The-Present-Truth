package com.piestack.adventelegraph.di.qualifiers;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by Jeffrey Nyauke on 4/6/2017.
 */

@Scope
@Retention(value = RetentionPolicy.RUNTIME)
public @interface BibleDataScope {
}
