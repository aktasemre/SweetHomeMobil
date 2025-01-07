// Generated by view binder compiler. Do not edit!
package com.sweethome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.sweethome.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentAddCheckItemBinding implements ViewBinding {
  @NonNull
  private final ScrollView rootView;

  @NonNull
  public final TextInputEditText descriptionInput;

  @NonNull
  public final TextInputLayout descriptionInputLayout;

  @NonNull
  public final ImageView photoPreview;

  @NonNull
  public final MaterialButton saveButton;

  @NonNull
  public final TextInputEditText titleInput;

  @NonNull
  public final TextInputLayout titleInputLayout;

  private FragmentAddCheckItemBinding(@NonNull ScrollView rootView,
      @NonNull TextInputEditText descriptionInput, @NonNull TextInputLayout descriptionInputLayout,
      @NonNull ImageView photoPreview, @NonNull MaterialButton saveButton,
      @NonNull TextInputEditText titleInput, @NonNull TextInputLayout titleInputLayout) {
    this.rootView = rootView;
    this.descriptionInput = descriptionInput;
    this.descriptionInputLayout = descriptionInputLayout;
    this.photoPreview = photoPreview;
    this.saveButton = saveButton;
    this.titleInput = titleInput;
    this.titleInputLayout = titleInputLayout;
  }

  @Override
  @NonNull
  public ScrollView getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentAddCheckItemBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentAddCheckItemBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_add_check_item, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentAddCheckItemBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.descriptionInput;
      TextInputEditText descriptionInput = ViewBindings.findChildViewById(rootView, id);
      if (descriptionInput == null) {
        break missingId;
      }

      id = R.id.descriptionInputLayout;
      TextInputLayout descriptionInputLayout = ViewBindings.findChildViewById(rootView, id);
      if (descriptionInputLayout == null) {
        break missingId;
      }

      id = R.id.photoPreview;
      ImageView photoPreview = ViewBindings.findChildViewById(rootView, id);
      if (photoPreview == null) {
        break missingId;
      }

      id = R.id.saveButton;
      MaterialButton saveButton = ViewBindings.findChildViewById(rootView, id);
      if (saveButton == null) {
        break missingId;
      }

      id = R.id.titleInput;
      TextInputEditText titleInput = ViewBindings.findChildViewById(rootView, id);
      if (titleInput == null) {
        break missingId;
      }

      id = R.id.titleInputLayout;
      TextInputLayout titleInputLayout = ViewBindings.findChildViewById(rootView, id);
      if (titleInputLayout == null) {
        break missingId;
      }

      return new FragmentAddCheckItemBinding((ScrollView) rootView, descriptionInput,
          descriptionInputLayout, photoPreview, saveButton, titleInput, titleInputLayout);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
