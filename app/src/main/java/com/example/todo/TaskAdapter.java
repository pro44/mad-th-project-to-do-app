package com.example.todo;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.db.Tasks;
import com.example.todo.db.TodoDatabase;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter {
    List<Tasks> list;
    Context context;
    String months[]={"","Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
    public TaskAdapter(List<Tasks> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent,false);
        ViewHolderClass viewHolderClass = new ViewHolderClass(view);

        return viewHolderClass;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolderClass vHClass = (ViewHolderClass) holder;
        Tasks model = list.get(position);

        vHClass.dayiw.setText(model.getDayiw());
        vHClass.dayic.setText(model.getDayic());
        vHClass.month.setText(months[Integer.parseInt(model.getMonth())]);
        vHClass.title.setText(model.getTitle());
        vHClass.aboutActivity.setText(model.getAboutActivity());
        vHClass.time.setText(model.getTime());
        vHClass.status.setText(model.getStatus());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    //view holder class
    public class ViewHolderClass extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        TextView dayiw,dayic,month,title,aboutActivity,time,status;
        ImageView iv;
        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);
            dayiw=itemView.findViewById(R.id.dayiw);
            dayic=itemView.findViewById(R.id.dayic);
            month=itemView.findViewById(R.id.month);
            title=itemView.findViewById(R.id.title);
            aboutActivity=itemView.findViewById(R.id.aboutActivity);
            time=itemView.findViewById(R.id.time);
            status=itemView.findViewById(R.id.status);
            iv=itemView.findViewById(R.id.menuImage);
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context.getApplicationContext(), "press for a while to show operations", Toast.LENGTH_LONG).show();

                }
            });
            iv.setOnCreateContextMenuListener(this);



        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.add(this.getAdapterPosition(),121,0,"delete");
            contextMenu.add(this.getAdapterPosition(),122,1,"update");
            contextMenu.add(this.getAdapterPosition(),123,2,"complete");
        }
    }

    public void nofity(){
        notifyDataSetChanged();
    }
    public void removeItem(int dattabasePosition,int position,List<Tasks> l){

        TodoDatabase.getDbInstance(context.getApplicationContext()).tasksDao().deleteFromTaskId(dattabasePosition);
        l.remove(position);
        if(list.size()<=0){
            TodoDatabase.getDbInstance(context.getApplicationContext()).tasksDao().truncateTheTable();
        }
        notifyDataSetChanged();
    }

}

