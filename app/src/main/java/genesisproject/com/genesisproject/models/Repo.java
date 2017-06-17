package genesisproject.com.genesisproject.models;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import lombok.Getter;

/**
 * Created by nsmirosh on 6/17/17.
 */

@Getter
@Parcel
public class Repo {


    public Repo(){}

    String name;

    @SerializedName("html_url")
    String url;



}
