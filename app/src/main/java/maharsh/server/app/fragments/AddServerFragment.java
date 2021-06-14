package maharsh.server.app.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;

import maharsh.server.app.R;
import maharsh.server.app.datamodel.ServerTable;
import maharsh.server.app.utils.IpUtils;
import maharsh.server.app.viewmodel.SavedServerViewModel;

public class AddServerFragment extends Fragment {

    TextInputLayout serverNameTextLayout,serverAddressTextLayout,serverMacTextLayout,serverPortTextLayout,serverSubnetTextLayout;
    TextInputEditText serverNameEditText,serverAddressEditText,serverMacEditText,serverPortEditText,serverSubnetEditText;
    SwitchMaterial shutdownSwitch;
    MaterialButton addButton;
    View rootView;
    SavedServerViewModel savedServerViewModel;
    NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_add_server, container, false);
        serverNameTextLayout = rootView.findViewById(R.id.servername_textInputLayout);
        serverAddressTextLayout = rootView.findViewById(R.id.serverAddress_textInputLayout);
        serverMacTextLayout = rootView.findViewById(R.id.serverMACAddress_textInputLayout);
        serverPortTextLayout = rootView.findViewById(R.id.serverPort_textInputLayout);
        serverNameEditText = rootView.findViewById(R.id.servername_textInputEditText);
        serverAddressEditText = rootView.findViewById(R.id.serverAddress_textInputEditText);
        serverMacEditText = rootView.findViewById(R.id.serverMACAddress_textInputEditText);
        serverPortEditText = rootView.findViewById(R.id.serverPort_textInputEditText);
        serverSubnetEditText=rootView.findViewById(R.id.serverSubnet_textInputEditText);
        serverSubnetTextLayout = rootView.findViewById(R.id.serverSubnet_textInputLayout);
        navController = Navigation.findNavController(container);

        shutdownSwitch = rootView.findViewById(R.id.shutdown_switch);
        addButton = rootView.findViewById(R.id.add_server_btn);

        savedServerViewModel = new ViewModelProvider(this).get(SavedServerViewModel.class);

        addButton.setOnClickListener(v->{
            if(isDetailsCorrect())
            {
                String subnet = serverSubnetEditText.getText().toString();

                int isShutdownSwitchChecked;
                if(shutdownSwitch.isChecked())
                    isShutdownSwitchChecked=1;
                else
                    isShutdownSwitchChecked=0;
                new Thread(() ->{
                    try {
                        savedServerViewModel.addServerToDB(new ServerTable(Objects.requireNonNull(serverNameEditText.getText()).toString(),
                                Objects.requireNonNull(serverAddressEditText.getText()).toString(),
                                Integer.parseInt(Objects.requireNonNull(serverPortEditText.getText()).toString()),
                                IpUtils.getBroadcastAddress(getIpAddress(serverAddressEditText.getText().toString())+getSubnet(subnet)),
                                Objects.requireNonNull(serverMacEditText.getText()).toString(),
                                isShutdownSwitchChecked

                        ));

                        new Handler(Looper.getMainLooper()).postDelayed(()->{
                            navController.navigateUp();
                        },200);
                    } catch (UnknownHostException e) {
                        Log.e("Error",e.getLocalizedMessage());
                        e.printStackTrace();
                    }
            }).start();

            }
        });

        serverMacEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                serverMacTextLayout.setErrorEnabled(false);
            }
        });
        serverAddressEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                serverAddressTextLayout.setErrorEnabled(false);
            }
        });
        serverPortEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                serverPortTextLayout.setErrorEnabled(false);
            }
        });
        serverNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                serverNameTextLayout.setErrorEnabled(false);
            }
        });
        serverSubnetEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                serverSubnetTextLayout.setErrorEnabled(false);
            }
        });


        return rootView;
    }
    private String getIpAddress(String address) throws UnknownHostException {
        String[] network = InetAddress.getByName(address).toString().split("/");
        return network[1];
    }

    private boolean isDetailsCorrect() {
        return verifyPort(Objects.requireNonNull(serverPortEditText.getText()).toString())
                && verifyMac(Objects.requireNonNull(serverMacEditText.getText()).toString())
                && verifyName(Objects.requireNonNull(serverNameEditText.getText()).toString())
                && verifyNetworkAddress(Objects.requireNonNull(serverAddressEditText.getText()).toString())
                && verifySubnet(serverSubnetEditText.getText().toString());
    }

    private boolean verifyNetworkAddress(String networkAddress) {
        if(networkAddress.isEmpty())
        {
            serverAddressTextLayout.setErrorEnabled(true);
            serverAddressTextLayout.setError("Enter Network Address");
            return false;
        }
        else
            return true;
    }

    private boolean verifyName(String name) {
        if(name.isEmpty())
        {
            serverNameTextLayout.setErrorEnabled(true);
            serverNameTextLayout.setError("Enter Server Name");
            return false;
        }
        else
            return true;
    }

    private boolean verifyPort(String port) {
        int portNumber = Integer.parseInt(port);
        if(portNumber >= 0 && portNumber < 65535 && !port.isEmpty())
        {
            return true;
        }
        else{
            serverPortTextLayout.setErrorEnabled(true);
            serverPortTextLayout.setError("Enter Correct Port");
            return false;
        }

    }

    private boolean verifyMac(String mac) {
        String[] splitMac = mac.split("[:\\-]");
        if(splitMac.length != 6 && mac.isEmpty())
        {
            serverMacTextLayout.setErrorEnabled(true);
            serverMacTextLayout.setError("Enter Mac Properly");
            return false;
        }
        else
            return true;
    }

    private boolean verifySubnet(String subnet) {
        if(!subnet.isEmpty())
        {
            String[] splitSubnet = subnet.split("\\.");
            if(splitSubnet.length==1)
            {
                return true;
            }
            else if(splitSubnet.length==4)
            {
                if(IpUtils.getCIDRFromSubnet(subnet)==null){
                    serverSubnetTextLayout.setErrorEnabled(true);
                    serverSubnetTextLayout.setError("Enter Subnet Properly");
                    return false;
                }
                else
                    return true;
            }
            else{
                serverSubnetTextLayout.setErrorEnabled(true);
                serverSubnetTextLayout.setError("Enter Subnet Properly");
                return false;
            }
        }
        else{
            serverSubnetTextLayout.setErrorEnabled(true);
            serverSubnetTextLayout.setError("Enter Subnet Properly");
            return false;
        }
    }
    private String getSubnet(String subnet) {
        String[] splitSubnet = subnet.split("\\.");
            if(splitSubnet.length==1)
            {
               return "/"+splitSubnet[0];
            }
            else
                return "/"+ IpUtils.getCIDRFromSubnet(subnet);
    }
}