package com.chettapps.invitationmaker.photomaker.handler;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;


import com.chettapps.invitationmaker.R;
import com.chettapps.invitationmaker.photomaker.main.CreateCardActivity;

/* loaded from: classes2.dex */
public class BlurOperationAsync extends AsyncTask<String, Void, String> {
    Activity context;
    private ProgressDialog pd;

    /* JADX INFO: Access modifiers changed from: protected */
    public String doInBackground(String... strArr) {
        return "yes";
    }

    public BlurOperationAsync(CreateCardActivity createCardActivity) {
        this.context = createCardActivity;
    }

    @Override // android.os.AsyncTask
    protected void onPreExecute() {
        ProgressDialog progressDialog = new ProgressDialog(this.context);
        this.pd = progressDialog;
        progressDialog.setMessage("Plase wait");
        this.pd.setCancelable(false);
        this.pd.show();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onPostExecute(String str) {
        this.pd.dismiss();
    }
}
