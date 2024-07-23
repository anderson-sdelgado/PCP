package br.com.usinasantafe.pcp.presenter.proprio

import br.com.usinasantafe.pcp.utils.FlowApp
import br.com.usinasantafe.pcp.utils.TypeAddOcupante
import br.com.usinasantafe.pcp.utils.TypeAddEquip

interface FragmentAttachListenerProprio {
    fun popBackStack()
    fun goInicial()
    fun goMovProprioList()
    fun goVeiculoProprio()
    fun goMatricColab()
    fun goNomeColab()
    fun goPassagList()
    fun goVeicSegList()
    fun goDestino()
    fun goNotaFiscal()
    fun goObserv()
    fun goDetalhe()
}