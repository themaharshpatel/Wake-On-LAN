package maharsh.server.app.preferences;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;
import androidx.preference.PreferenceViewHolder;
import androidx.preference.SwitchPreference;

import maharsh.server.app.R;


public class CustomSwitchPreference extends SwitchPreference {
    public CustomSwitchPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public CustomSwitchPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomSwitchPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomSwitchPreference(Context context) {
        super(context);
    }

    @Override
    public void onBindViewHolder(PreferenceViewHolder holder) {
        super.onBindViewHolder(holder);
        TextView title1 = (TextView) holder.findViewById(android.R.id.title);
        title1.setTypeface(ResourcesCompat.getFont(getContext(), R.font.noto_sans_regular));
        TextView title2 = (TextView) holder.findViewById(android.R.id.summary);
        title2.setTypeface(ResourcesCompat.getFont(getContext(), R.font.noto_sans_regular));
    }
}
