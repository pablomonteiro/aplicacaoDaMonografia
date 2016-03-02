package com.aplicacaodamonografia.view;

import android.support.test.espresso.PerformException;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.CoordinatesProvider;
import android.support.test.espresso.action.GeneralClickAction;
import android.support.test.espresso.action.PrecisionDescriber;
import android.support.test.espresso.action.Tap;
import android.support.test.espresso.action.Tapper;
import android.support.test.espresso.core.deps.guava.base.Optional;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.espresso.util.HumanReadables;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.view.ViewConfiguration;
import android.webkit.WebView;

import org.hamcrest.Matcher;

/**
 * Created by pablo on 01/03/16.
 *
 * See: http://baiduhix.blogspot.com.br/2015/07/android-espresso-test-with-viewlist.html
 *
 */
public class ButtonGeneralClickAction implements ViewAction {

    private final CoordinatesProvider coordinatesProvider;
    private final Tapper tapper;
    private final PrecisionDescriber precisionDescriber;
    private final Optional<ViewAction> rollbackAction;

    public ButtonGeneralClickAction(CoordinatesProvider coordinatesProvider, Tapper tapper, PrecisionDescriber precisionDescriber, ViewAction rollbackAction) {
        this.coordinatesProvider = coordinatesProvider;
        this.tapper = tapper;
        this.precisionDescriber = precisionDescriber;
        this.rollbackAction = Optional.fromNullable(rollbackAction);
    }

    @Override
    public Matcher<View> getConstraints() {
//        Matcher<View> standardConstraint = isDisplayingAtLeast(90);
//        if (rollbackAction.isPresent()) {
//            return AllOf.allOf(standardConstraint, rollbackAction.get().getConstraints());
//        } else {
//            return standardConstraint;
//        }
        return ViewMatchers.isClickable();
    }

    @Override
    public String getDescription() {
        return tapper.toString().toLowerCase() + " click";
    }

    @Override
    public void perform(UiController uiController, View view) {

        float[] coordinates = coordinatesProvider.calculateCoordinates(view);
        float[] precision = precisionDescriber.describePrecision();
        Tapper.Status status = Tapper.Status.FAILURE;
        int loopCount = 0;
        while (status != Tapper.Status.SUCCESS && loopCount < 3) {
            try {
                status = tapper.sendTap(uiController, coordinates, precision);
            } catch (RuntimeException re) {
                boolean isCLicked = ((AppCompatButton) view).callOnClick();
                if(isCLicked) {
                    status = Tapper.Status.SUCCESS;
                }
            }

            int duration = ViewConfiguration.getPressedStateDuration();
            // ensures that all work enqueued to process the tap has been run.
            if (duration > 0) {
                uiController.loopMainThreadForAtLeast(duration);
            }
            if (status == Tapper.Status.WARNING) {
                if (rollbackAction.isPresent()) {
                    rollbackAction.get().perform(uiController, view);
                } else {
                    break;
                }
            }
            loopCount++;
        }
        if (status == Tapper.Status.FAILURE) {
            throw new PerformException.Builder()
                    .withActionDescription(this.getDescription())
                    .withViewDescription(HumanReadables.describe(view))
                    .withCause(new RuntimeException(String.format("Couldn't "
                                    + "click at: %s,%s precision: %s, %s . Tapper: %s coordinate provider: %s precision " +
                                    "describer: %s. Tried %s times. With Rollback? %s", coordinates[0], coordinates[1],
                            precision[0], precision[1], tapper, coordinatesProvider, precisionDescriber, loopCount,
                            rollbackAction.isPresent())))
                    .build();
        }

        if (tapper == Tap.SINGLE && view instanceof WebView) {
            // WebViews will not process click events until double tap
            // timeout. Not the best place for this - but good for now.
            uiController.loopMainThreadForAtLeast(ViewConfiguration.getDoubleTapTimeout());
        }

    }
}
