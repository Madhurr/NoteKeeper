import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.madhur.notekeeper.NoteInfo;
import com.example.madhur.notekeeper.R;

import java.util.List;

/**
 * Created by madhur on 7/7/18.
 */

public class NoteRecyclerAdapter extends RecyclerView.Adapter<NoteRecyclerAdapter.ViewHolder>{
    private final Context mContext;
    private final LayoutInflater inflater;
    private final List<NoteInfo> mNotes;

    public NoteRecyclerAdapter(Context mContext, List<NoteInfo> mNotes) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        this.mNotes = mNotes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_note_list,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {

        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public final TextView textCourse;
        public final TextView textTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            textCourse = (TextView) itemView.findViewById(R.id.text_course);
            textTitle = (TextView) itemView.findViewById(R.id.text_title);
        }
    }
}
