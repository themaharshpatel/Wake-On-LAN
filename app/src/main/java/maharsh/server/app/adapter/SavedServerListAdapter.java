package maharsh.server.app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

import java.net.SocketException;
import java.net.UnknownHostException;

import maharsh.server.app.R;
import maharsh.server.app.datamodel.ServerTable;
import maharsh.server.app.utils.SendWakeup;

public class SavedServerListAdapter extends ListAdapter<ServerTable,SavedServerListAdapter.SavedServerViewHolder> {


    public SavedServerListAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<ServerTable> DIFF_CALLBACK = new DiffUtil.ItemCallback<ServerTable>() {
        @Override
        public boolean areItemsTheSame(@NonNull ServerTable oldItem, @NonNull ServerTable newItem) {
            return oldItem.getId()==newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull ServerTable oldItem, @NonNull ServerTable newItem) {
            return oldItem.getServerName().equals(newItem.getServerName()) &&
                    oldItem.getServerAddress().equals(newItem.getServerAddress()) &&
                    oldItem.getIsShutDownEnabled()==newItem.getIsShutDownEnabled() &&
                    oldItem.getServerBroadcastAddress().equals(newItem.getServerBroadcastAddress()) &&
                    oldItem.getServerPort()==newItem.getServerPort() ;
        }
    };

    @NonNull
    @Override
    public SavedServerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SavedServerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.server_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SavedServerViewHolder holder, int position) {
        ServerTable savedServer = getItem(position);
        holder.serverName.setText(savedServer.getServerName());
        if(savedServer.getIsShutDownEnabled()==0)
            holder.shutdownButton.setVisibility(View.GONE);
        else
            holder.shutdownButton.setVisibility(View.VISIBLE);

        holder.wakeUpButton.setOnClickListener(v->{
            new Thread(()->{
                try {
                    SendWakeup.to(savedServer);
                    Snackbar.make(holder.itemView,"Wake Up Sent",Snackbar.LENGTH_SHORT).show();
                } catch (SocketException | UnknownHostException e) {
                    e.printStackTrace();
                }
            }).start();
        });

        holder.shutdownButton.setOnClickListener(v->{
            RequestQueue queue = Volley.newRequestQueue(holder.itemView.getContext());
            StringRequest request = new StringRequest(Request.Method.GET, "http://"+savedServer.getServerAddress()+"/shutdown", response -> {
                Toast.makeText(holder.itemView.getContext(),response.toString(),Toast.LENGTH_LONG).show();
            }, error -> {
                Toast.makeText(holder.itemView.getContext(),error.getLocalizedMessage(),Toast.LENGTH_LONG).show();
            });

            queue.add(request);
        });

    }

    public static class SavedServerViewHolder extends RecyclerView.ViewHolder {
        MaterialButton shutdownButton, wakeUpButton;;
        TextView serverName;
        public SavedServerViewHolder(@NonNull View itemView) {
            super(itemView);
            serverName = itemView.findViewById(R.id.server_name_TextView);
            shutdownButton =itemView.findViewById(R.id.shutdown_button);
            wakeUpButton= itemView.findViewById(R.id.wakeup_button);
        }
    }

}
