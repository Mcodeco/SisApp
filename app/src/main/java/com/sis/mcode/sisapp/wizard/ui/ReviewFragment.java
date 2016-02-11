package com.sis.mcode.sisapp.wizard.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.sis.mcode.sisapp.ConsultaAfiliadoActivity;
import com.sis.mcode.sisapp.R;
import com.sis.mcode.sisapp.wizard.model.AbstractWizardModel;
import com.sis.mcode.sisapp.wizard.model.ModelCallbacks;
import com.sis.mcode.sisapp.wizard.model.Page;
import com.sis.mcode.sisapp.wizard.model.ReviewItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ReviewFragment extends ListFragment implements ModelCallbacks {

    private Callbacks mCallbacks;
    private AbstractWizardModel mWizardModel;
    private List<ReviewItem> mCurrentReviewItems;

    private ReviewAdapter mReviewAdapter;

    public ReviewFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mReviewAdapter = new ReviewAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_page, container, false);

        TextView titleView = (TextView) rootView.findViewById(android.R.id.title);
        titleView.setText(R.string.review);
        titleView.setTextColor(getResources().getColor(R.color.step_pager_selected_tab_color));

        ListView listView = (ListView) rootView.findViewById(android.R.id.list);
        setListAdapter(mReviewAdapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (!(activity instanceof Callbacks)) {
            throw new ClassCastException("Activity must implement fragment's callbacks");
        }

        mCallbacks = (Callbacks) activity;

        mWizardModel = mCallbacks.onGetModel();
        mWizardModel.registerListener(this);
        onPageTreeChanged();
    }

    @Override
    public void onPageTreeChanged() {
        onPageDataChanged(null);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;

        mWizardModel.unregisterListener(this);
    }

    @Override
    public void onPageDataChanged(Page changedPage) {
        ArrayList<ReviewItem> reviewItems = new ArrayList<ReviewItem>();
        for (Page page : mWizardModel.getCurrentPageSequence()) {
            page.getReviewItems(reviewItems);
        }
        mCurrentReviewItems = reviewItems;

        if (mReviewAdapter != null) {
            mReviewAdapter.notifyDataSetInvalidated();
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        mCallbacks.onEditScreenAfterReview(mCurrentReviewItems.get(position).getPageKey());
    }

    public interface Callbacks {
        AbstractWizardModel onGetModel();
        void onEditScreenAfterReview(String pageKey);
    }

    private class ReviewAdapter extends BaseAdapter {
        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public int getItemViewType(int position) {
            return 0;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public boolean areAllItemsEnabled() {
            return true;
        }

        @Override
        public Object getItem(int position) {
            return mCurrentReviewItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return mCurrentReviewItems.get(position).hashCode();
        }

        @Override
        public View getView(int position, View view, ViewGroup container) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View rootView = inflater.inflate(R.layout.list_item_review, container, false);

            ReviewItem reviewItem = mCurrentReviewItems.get(position);
            String value = reviewItem.getDisplayValue();

            if (TextUtils.isEmpty(value)) {
                value = "";
            }

            switch (position) {
                case 0:
                    ConsultaAfiliadoActivity.TIPO_CONSULTA =value;
                    break;
                case 1:
                    ConsultaAfiliadoActivity.TIPO_DOCUMENTO = value == "DNI" ? 1 : 2;
                    ConsultaAfiliadoActivity.APELLIDO_PATERNO =value;
                    break;
                case 2:
                    ConsultaAfiliadoActivity.NRO_DOCUMENTO = value;
                    ConsultaAfiliadoActivity.APELLIDO_MATERNO = value;
                    break;
                case 3:
                    ConsultaAfiliadoActivity.PRIMER_NOMBRE = value;
                    break;
                case 4:
                    ConsultaAfiliadoActivity.SEGUNDO_NOMBRE = value;
                    break;
            }

            ((TextView) rootView.findViewById(android.R.id.text1)).setText(reviewItem.getTitle());
            ((TextView) rootView.findViewById(android.R.id.text2)).setText(value);
            return rootView;
        }

        @Override
        public int getCount() {
            return mCurrentReviewItems.size();
        }
    }

}