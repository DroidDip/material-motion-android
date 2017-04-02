/*
 * Copyright 2016-present The Material Motion Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.android.material.motion.testing;

import com.google.android.indefinite.observable.IndefiniteObservable;
import com.google.android.material.motion.BuildConfig;
import com.google.android.material.motion.MotionObserver;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static com.google.common.truth.Truth.assertThat;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class SimulatedMotionSourceTests {

  private static final float E = 0.0001f;

  private SimulatedMotionSource<Float> source;

  @Before
  public void setUp() {
    source = new SimulatedMotionSource<>();
  }

  @Test
  public void passesValueAndState() {
    IndefiniteObservable.Subscription assertion =
      assertThatNextValuesWillBeEqualTo(source, 5f);
    source.next(5f);
    assertion.unsubscribe();

    assertion =
      assertThatNextValuesWillBeEqualTo(source, 7f);
    source.next(7f);
    assertion.unsubscribe();
  }

  @Test
  public void canPassValuesWithNoObserver() {
    source.next(5f);
  }

  private static <T> IndefiniteObservable.Subscription assertThatNextValuesWillBeEqualTo(
    SimulatedMotionSource<T> source, final T expectedValue) {
    return source.getObservable().subscribe(new MotionObserver<T>() {
      @Override
      public void next(T value) {
        assertThat(value).isEqualTo(expectedValue);
      }
    });
  }
}
