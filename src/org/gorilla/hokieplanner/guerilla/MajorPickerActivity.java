package org.gorilla.hokieplanner.guerilla;

import android.content.Intent;
import android.view.View;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.app.ListActivity;
import android.os.Bundle;

/**
 * Activity where the user selects the specific checksheet based on major and
 * graduation year
 *
 * @author Anirudh Bagde (anibagde)
 * @author Weyland Chiang (chiangw)
 * @author Sayan Ekambarapu (sayan96)
 * @version Nov 9, 2014
 */
public class MajorPickerActivity
    extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Populate the list using values from the enum
        setListAdapter(new ArrayAdapter<AvailableChecksheets>(
            this,
            android.R.layout.simple_list_item_1,
            android.R.id.text1,
            AvailableChecksheets.values()));
    }

    @Override
    protected void onListItemClick(
        ListView l,
        View v,
        int position,
        long id) {
        // Save the selected checksheet to a global location and let
        // PlannerActivity take over
        AvailableChecksheets checksheet =
            AvailableChecksheets.values()[position];
        Prefs.setSelectedChecksheet(checksheet.name());
        Intent intent = new Intent(this, PlannerActivity.class);
        startActivity(intent);
        finish();
    }
}
