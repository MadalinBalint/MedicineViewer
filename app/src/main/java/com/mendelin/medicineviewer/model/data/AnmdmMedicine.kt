package com.mendelin.medicineviewer.model.data

class AnmdmMedicine {
    var denumire_com: String = ""
    var dci: String = ""
    var ff: String = ""
    var conc: String = ""
    var atc: String = ""
    var actiune_terap: String = ""
    var rp: String = ""
    var ambalaj: String = ""
    var volum_ambalaj: String = ""
    var valabilitate: String = ""
    var cim: String = ""
    var app_prod: String = ""
    var app_detinator: String = ""
    var nr_app: String = ""
    var rcp_url: String = ""
    var prospect_url: String = ""
    var ambalaj_url: String = ""

    override fun toString(): String {
        return "AnmdmMedicine(denumire_com='$denumire_com', dci='$dci', ff='$ff', conc='$conc', atc='$atc', actiune_terap='$actiune_terap', rp='$rp', ambalaj='$ambalaj', volum_ambalaj='$volum_ambalaj', valabilitate='$valabilitate', cim='$cim', app_prod='$app_prod', app_detinator='$app_detinator', nr_app='$nr_app', rcp_url='$rcp_url', prospect_url='$prospect_url', ambalaj_url='$ambalaj_url')"
    }
}