package br.com.usinasantafe.pcp.utils

fun getClassAndMethod(): String {
    return Thread.currentThread().stackTrace
        .firstOrNull { element ->
            element.fileName?.endsWith(".kt") == true &&
                    element.methodName != "getClassAndMethod"
        }?.let { element ->
            // Ajusta a classe: tira tudo após $ (ex: $recoverMovOpenList$1)
            val className = element.className
                .substringAfterLast('.')     // Pega apenas o nome simples da classe
                .substringBefore('$')        // Remove o que vem depois de "$"

            // Ajusta o método: tenta extrair de dentro do className, se possível
            val methodNameFromClass = element.className
                .substringAfter("$")         // Tenta pegar o nome do método de dentro do className
                .substringBefore('$')        // Remove sufixos como $1

            val methodName = methodNameFromClass.ifBlank { element.methodName }

            "$className.$methodName"
        } ?: "Classe.MétodoDesconhecido"
}