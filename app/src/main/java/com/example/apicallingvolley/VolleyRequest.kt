package com.example.apicallingvolley

import android.content.Context
import android.graphics.Bitmap
import android.util.LruCache
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class VolleyRequest {
    private var mRequestQueue: RequestQueue? = null
    private var context: Context? = null
    private var iVolley: VolleyInterface? = null
    var imageLoader: ImageLoader? = null
        private set

    val requestQueue: RequestQueue
        get() {
            if (mRequestQueue == null) {
                mRequestQueue = Volley.newRequestQueue(context!!.applicationContext)
            }

            return mRequestQueue!!
        }

    private constructor(context: Context, iVolley: VolleyInterface) {
        this.context = context
        this.iVolley = iVolley
        mRequestQueue = requestQueue
        this.imageLoader = ImageLoader(mRequestQueue, object: ImageLoader.ImageCache {
            private val mCache = LruCache<String, Bitmap>(10)
            override fun getBitmap(url: String?): Bitmap? {
                return mCache.get(url)
            }

            override fun putBitmap(url: String?, bitmap: Bitmap?) {
                mCache.put(url, bitmap)
            }

        })
    }

    private constructor(context: Context) {
        this.context = context
        mRequestQueue = requestQueue
        this.imageLoader = ImageLoader(mRequestQueue, object: ImageLoader.ImageCache {
            private val mCache = LruCache<String, Bitmap>(10)
            override fun getBitmap(url: String?): Bitmap? {
                return mCache.get(url)
            }

            override fun putBitmap(url: String?, bitmap: Bitmap?) {
                mCache.put(url, bitmap)
            }

        })
    }

    fun <T> addToRequestQueue(req: Request<T>) {
        requestQueue.add(req)
    }

    //GET
    fun getRequest(url: String) {
        val getRequest = JsonObjectRequest(Request.Method.GET, url, null, { response ->
            iVolley!!.onResponse(response.toString())
        }, { error ->
            iVolley!!.onResponse(error.message!!)
        })

        addToRequestQueue(getRequest)
    }

    //POST with params
    fun postRequest(url: String) {
        val postRequest = object: StringRequest(
            Method.POST, url,
            Response.Listener { response ->
                iVolley!!.onResponse(response.toString())
            }, Response.ErrorListener { error ->
                iVolley!!.onResponse(error.message!!)
            }
        ){
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["name"] = "Eddy Lee"
                params["value"] = "This is value posted from an android app"
                return params
            }
        }

        addToRequestQueue(postRequest)
    }

    //PUT with params
    fun putRequest(url: String) {
        val postRequest = object: StringRequest(
            Method.PUT, url,
            Response.Listener { response ->
                iVolley!!.onResponse(response.toString())
            }, Response.ErrorListener { error ->
                iVolley!!.onResponse(error.message!!)
            }
        ){
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["name"] = "Eddy Lee"
                params["value"] = "This is value put from an android app"
                return params
            }
        }

        addToRequestQueue(postRequest)
    }

    //PATCH with params
    fun patchRequest(url: String) {
        val postRequest = object: StringRequest(
            Method.PATCH, url,
            Response.Listener { response ->
                iVolley!!.onResponse(response.toString())
            }, Response.ErrorListener { error ->
                iVolley!!.onResponse(error.message!!)
            }
        ){
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["name"] = "Eddy Lee"
                params["value"] = "This is value patch from an android app"
                return params
            }
        }

        addToRequestQueue(postRequest)
    }

    //DELETE
    fun deleteRequest(url: String) {
        val deleteRequest = StringRequest(Request.Method.DELETE, url, { response ->
            iVolley!!.onResponse(response)
        }, { error ->
            iVolley!!.onResponse(error.message!!)
        })

        addToRequestQueue(deleteRequest)
    }

    companion object {
        private var mInstance: VolleyRequest? = null
        @Synchronized
        fun getInstance(context: Context): VolleyRequest {
            if (mInstance == null) {
                mInstance = VolleyRequest(context)
            }

            return mInstance!!
        }

        @Synchronized
        fun getInstance(context: Context, iVolley: VolleyInterface): VolleyRequest {
            if (mInstance == null) {
                mInstance = VolleyRequest(context, iVolley)
            }

            return mInstance!!
        }
    }
}