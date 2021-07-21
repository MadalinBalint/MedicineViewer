package com.mendelin.medicineviewer.repository.remote

import com.mendelin.medicineviewer.model.data.AnmdmInfo
import com.mendelin.medicineviewer.model.data.AnmdmMedicine
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import java.text.SimpleDateFormat
import java.util.*

class MedicineRepo {
    fun parsePageInfo(html: String): AnmdmInfo {
        val doc: Document = Jsoup.parse(html)
        val ul: Element? = doc.select("ul").first()
        val lis: Elements? = ul?.select("li")

        val total = lis?.get(5)?.text()?.toInt() ?: 0

        val divs: Elements = doc.select("div")
        for (div in divs) {
            val text = div.text()
                .replace("Actualizat în", "")
                .trim()
            if (text.length == 10) {
                val format = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
                val date = format.parse(text)
                date?.let {
                    return AnmdmInfo(total, date)
                }
            }
        }

        return AnmdmInfo(0, Date())
    }

    fun parseMedicines(html: String): List<AnmdmMedicine> {
        val doc: Document = Jsoup.parse(html)
        val list = mutableListOf<AnmdmMedicine>()

        val table = doc.select("tbody").first()
        val trs = table?.select("tr")

        trs?.let {
            for (tr in trs) {
                val td = tr.select("td").first()?.select("button")?.first()
                td?.let {
                   val medicine = parseAnmdmMedicineData(td)
                   list.add(medicine)
                }
            }
        }

        return list
    }

    private fun parseAnmdmMedicineData(td: Element): AnmdmMedicine {
        val anm = AnmdmMedicine()

        anm.denumire_com = td.attr("data-dencom")
        anm.dci = td.attr("data-dci").trim { it <= ' ' }
        anm.ff = td.attr("data-formafarm")
        anm.conc = td.attr("data-conc")
        anm.atc = td.attr("data-codatc").trim { it <= ' ' }
        anm.actiune_terap = td.attr("data-actter")
        anm.rp = td.attr("data-prescript")
        anm.ambalaj = td.attr("data-ambalaj")
        anm.volum_ambalaj = td.attr("data-volumamb")
        anm.valabilitate = td.attr("data-valabamb")
        anm.cim = td.attr("data-cim").trim { it <= ' ' }
        anm.app_prod = td.attr("data-firmtarp")
        anm.app_detinator = td.attr("data-firmtard")
        anm.nr_app = td.attr("data-nrdtamb").trim { it <= ' ' }
        anm.rcp_url = td.attr("data-linkrcp")
        anm.prospect_url = td.attr("data-linkpro").trim { it <= ' ' }
        anm.ambalaj_url = td.attr("data-linkamb").trim { it <= ' ' }

        return anm
    }
}