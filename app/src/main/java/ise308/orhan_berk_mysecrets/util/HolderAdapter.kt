package ise308.orhan_berk_mysecrets.util

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.text.format.DateFormat
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
import ise308.orhan_berk_mysecrets.fragment.UpdateSecretFragment
import java.util.*


class HolderAdapter() : RecyclerView.Adapter<HolderAdapter.holderStructure>(){

    lateinit var mainActivity : MainActivity
    lateinit var dbHelper : DatabaseHelper
    private var context : Context? = null
    private var secretList : ArrayList<HolderStructure>? = null

    var seciliRenk = 0
    var cardColor = 0
    var diaryDate: String = ""


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
        // Setting Items infos inside of ListView
        val model = secretList!!.get(position)
        val id = model.id
        val title = model.title
        val desc = model.description
        val hLight = model.highlight.toInt()
        val dTime = model.diaryTime.toLong()
        val dLoc = model.diaryLocation

        holder.txtTitle.text = title
        holder.txtDesc.text = desc

        diaryDate = DateFormat.format("dd/MM/yyyy", Date(dTime)).toString()

        holder.itemLocation.text = dLoc
        holder.itemTime.text = diaryDate

        checkHighLightColor()
        val textColor = ContextCompat.getColor(context!!, R.color.white)

        // Checking Highlighted Color and Changing The Background Of CardView
        when (seciliRenk) {
            0 ->  { cardColor = ContextCompat.getColor(context!!, R.color.hColor2) }
            1 ->  { cardColor = ContextCompat.getColor(context!!, R.color.hColor1) }
            2 ->  { cardColor = ContextCompat.getColor(context!!, R.color.hColor2) }
            3 ->  { cardColor = ContextCompat.getColor(context!!, R.color.hColor3) }
            4 ->  { cardColor = ContextCompat.getColor(context!!, R.color.hColor4) }
        }

        // If Diary is Highlighted Then Change The Color Of Background
        if (hLight == 1){
            holder.cardView.setCardBackgroundColor(cardColor)
            holder.txtDesc.setTextColor(textColor)
            holder.txtTitle.setTextColor(textColor)
            holder.itemTime.setTextColor(textColor)
            holder.itemLocation.setTextColor(textColor)
        }

        // Opening Diary
        holder.openSecretButton.setOnClickListener {

           openSecret(id)

        }

        // Editing Diary
        holder.editButton.setOnClickListener { 
            
            //updateSecret(id)
            updateSecretDialog(id)
            
        }

        // Deleting Diary
        holder.delButton.setOnClickListener {

            deleteSecret(id)

        }
    }


    // Delete Function
    private fun deleteSecret(id : String){
        val builder : AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setTitle((context as AppCompatActivity).getString(R.string.delQuestion)).setPositiveButton((context as AppCompatActivity).getString(R.string.delete)){
            dialogInterface, i ->   dbHelper.deleteSecret(id)
            (context as MainActivity)!!.onResume()
        }.setNegativeButton((context as AppCompatActivity).getString(R.string.cancel)) {
            dialogInterface, i ->
        }.show()
    }

    // Open Function
    private fun openSecret(id : String){
        val intent = Intent(context, ShowSecretActivity::class.java)
        intent.putExtra("S_ID", id)
        context!!.startActivity(intent)
    }

    // Update Function With Activity
    private fun updateSecret(id: String) {
        val intent = Intent(context, UpdateSecretActivity::class.java)
        intent.putExtra("S_ID", id)
        context!!.startActivity(intent)
    }

    // Update Function With Fragment
    private fun updateSecretDialog(id : String){
        val dialog = UpdateSecretFragment()
        dialog.getSecretID(id)
        dialog.show((context as AppCompatActivity).supportFragmentManager, "")
    }

    // CardView Structure
    inner class holderStructure(itemView: View) : RecyclerView.ViewHolder(itemView){

        var openSecretButton : LinearLayout = itemView.findViewById(R.id.openContent)
        var txtTitle : TextView = itemView.findViewById(R.id.itemTitle)
        var txtDesc : TextView = itemView.findViewById(R.id.itemDescription)
        var editButton : Button = itemView.findViewById(R.id.editBTN)
        var delButton : Button = itemView.findViewById(R.id.delBTN)
        var cardView : CardView = itemView.findViewById(R.id.cardView)
        var itemLocation : TextView = itemView.findViewById(R.id.itemLocation)
        var itemTime : TextView = itemView.findViewById(R.id.itemTime)
    }


    fun checkHighLightColor(){
        val prefs = (context as AppCompatActivity).getSharedPreferences("MySecrets", Context.MODE_PRIVATE)
        seciliRenk = prefs.getInt("highLightRenk", 0)
    }

}