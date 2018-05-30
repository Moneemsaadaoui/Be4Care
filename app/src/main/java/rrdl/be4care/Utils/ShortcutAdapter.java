package rrdl.be4care.Utils;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.truizlop.sectionedrecyclerview.SimpleSectionedAdapter;

public class ShortcutAdapter extends SimpleSectionedAdapter<ShortcutAdapter.ViewHolder> {


    @Override
    protected String getSectionHeaderTitle(int section) {
        return null;
    }

    @Override
    protected int getSectionCount() {
        return 0;
    }

    @Override
    protected int getItemCountForSection(int section) {
        return 0;
    }

    @Override
    protected ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    protected void onBindItemViewHolder(ViewHolder holder, int section, int position) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
