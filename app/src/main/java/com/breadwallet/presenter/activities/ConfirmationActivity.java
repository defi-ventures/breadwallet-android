package com.breadwallet.presenter.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;

import com.breadwallet.R;
import com.breadwallet.presenter.customviews.BRButton;
import com.breadwallet.presenter.fragments.FragmentLinkWallet;
import com.breadwallet.protocols.messageexchange.MessageExchangeService;

import java.io.Serializable;

/**
 * Created by Jade Byfield <jade@breadwallet.com> on  7/24/18.
 * Copyright (c) 2018 breadwallet LLC
 * <p/>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p/>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p/>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

// This Activity will be able to populate 2 fragments, either FragmentLinkWallet or FragmentPaymentConfirmation
public class ConfirmationActivity extends FragmentActivity {


    private static final String TAG = ConfirmationActivity.class.getSimpleName();
    public static final String EXTRA_CONFIRMATION_TYPE = "extra_confirmation_type";

    public enum ConfirmationType {
        LINK,
        PAYMENT
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);
        Bundle bundle = getIntent().getExtras();

        BRButton positiveButton = findViewById(R.id.positive_button);
        BRButton negativeButton = findViewById(R.id.negative_button);

        if (bundle != null) {
            Log.d(TAG, "Found bundle!");
            ConfirmationType confirmationType = (ConfirmationType) bundle.getSerializable(EXTRA_CONFIRMATION_TYPE);
            Log.d(TAG, "Confirmation type -> " + confirmationType);
            if (confirmationType == (ConfirmationType.LINK)) {
                FragmentLinkWallet linkWalletFragment = FragmentLinkWallet.newInstance(bundle);
                getFragmentManager().beginTransaction().add(R.id.fragment_container, linkWalletFragment).commit();
                Log.d(TAG, "ConfirmationType LINK");

                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        handleLinkApproved();
                    }
                });

                negativeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        handleLinkDeclined();
                    }
                });

            } else if (confirmationType == ConfirmationType.PAYMENT) {
                // Display FragmentPaymentConfirmation and set up new listeners for positive
                // and negative buttons
            } else {
                Log.d(TAG, "Found unknown ConfirmationType!");
            }
        }
    }

    public void handleLinkDeclined() {
        Log.d(TAG, "handleLinkDeclined()");
        startService(MessageExchangeService.createIntent(this, false));
        finish();
    }

    public void handleLinkApproved() {
        Log.d(TAG, "handleLinkApproved()");
        startService(MessageExchangeService.createIntent(this, true));
        finish();
    }

}