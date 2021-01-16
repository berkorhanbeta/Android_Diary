package ise308.orhan_berk_mysecrets.util

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ise308.orhan_berk_mysecrets.R
import ise308.orhan_berk_mysecrets.activity.MainActivity
import ise308.orhan_berk_mysecrets.activity.ShowSecretActivity
import ise308.orhan_berk_mysecrets.activity.UpdateSecretActivity
import ise308.orhan_berk_mysecrets.database.DatabaseSecret
import ise308.orhan_berk_mysecrets.fragment.UpdateSecretFragment


class HolderAdapter() : RecyclerView.Adapter<HolderAdapter.holderStructure>(){

    lateinit var mainActivity : MainActivity
    lateinit var dbHelper : DatabaseHelper
    private var context : Context? = null
    private var secretList : ArrayList<HolderStructure>? = null

    var seciliRenk = 0
    var cardColor = 0

    constructor(context: Context?, secretList : ArrayList<HolderStructure>?) : this(){
        this.context = context
        this.secretList = secretList
        dbHelper = DatabaseHelper(context)
    }

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : holderStructure {

        return holderStructure(
                LayoutInflater.from(context).inflate(R.layout.design_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return secretList!!.size
    }


    override fun onBindViewHolder(holder: holderStructure, position: Int) {
        mainActivity = MainActivity()
        val model = secretList!!.get(position)
        val id = model.id
        val title = model.title
        val desc = model.description
        val hLight = model.highlight.toInt()

        holder.txtTitle.text = title
        holder.txtDesc.text = desc

        checkHighLightColor()
        val textColor = ContextCompat.getColor(context!!, R.color.white)

        when (seciliRenk) {
            0 ->  { cardColor = ContextCompat.getColor(context!!, R.color.hColor2) }
            1 ->  { cardColor = ContextCompat.getColor(context!!, R.color.hColor1) }
            2 ->  { cardColor = ContextCompat.getColor(context!!, R.color.hColor2) }
            3 ->  { cardColor = ContextCompat.getColor(context!!, R.color.hColor3) }
            4 ->  { cardColor = ContextCompat.getColor(context!!, R.color.hColor4) }
        }

        if (hLight == 1){
            holder.cardView.setCardBackgroundColor(cardColor)
            holder.txtDesc.setTextColor(textColor)
            holder.txtTitle.setTextColor(textColor)
        }

        holder.openSecretButton.setOnClickListener {

           openSecret(id)

        }
        
        holder.editButton.setOnClickListener { 
            
            //updateSecret(id)
            updateSecretDialog(id)
            
        }
        
        holder.delButton.setOnClickListener {

            deleteSecret(id)

        }
    }


    private fun deleteSecret(id : String){
        val builder : AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setTitle((context as AppCompatActivity).getString(R.string.delQuestion)).setPositiveButton((context as AppCompatActivity).getString(R.string.delete)){
            dialogInterface, i ->   dbHelper.deleteSecret(id)
            (context as MainActivity)!!.onResume()
        }.setNegativeButton((context as AppCompatActivity).getString(R.string.cancel)) {
            dialogInterface, i ->
        }.show()
    }

    private fun openSecret(id : String){
        val intent = Intent(context, ShowSecretActivity::class.java)
        intent.putExtra("S_ID", id)
        context!!.startActivity(intent)
    }

    private fun updateSecret(id: String) {
        val intent = Intent(context, UpdateSecretActivity::class.java)
        intent.putExtra("S_ID", id)
        context!!.startActivity(intent)
    }


    private fun updateSecretDialog(id : String){
        val dialog = UpdateSecretFragment()
        dialog.getSecretID(id)
        dialog.show((context as AppCompatActivity).supportFragmentManager, "")
    }

    inner class holderStructure(itemView: View) : RecyclerView.ViewHolder(itemView){

        var openSecretButton : LinearLayout = itemView.findViewById(R.id.openContent)
        var txtTitle : TextView = itemView.findViewById(R.id.itemTitle)
        var txtDesc : TextView = itemView.findViewById(R.id.itemDescription)
        var editButton : Button = itemView.findViewById(R.id.editBTN)
        var delButton : Button = itemView.findViewById(R.id.delBTN)
        var cardView : CardView = itemView.findViewById(R.id.cardView)

    }


    fun checkHighLightColor(){
        val prefs = (context as AppCompatActivity).getSharedPreferences("MySecrets", Context.MODE_PRIVATE)
        seciliRenk = prefs.getInt("highLightRenk", 0)
    }

}