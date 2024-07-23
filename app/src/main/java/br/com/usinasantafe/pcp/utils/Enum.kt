package br.com.usinasantafe.pcp.utils

enum class StatusData { OPEN, CLOSE  }
enum class StatusSend { SEND, SENDING, SENT }
enum class StatusForeigner { INSIDE, OUTSIDE }
enum class TypeMov { INPUT, OUTPUT, EMPTY }
enum class StatusUpdate { UPDATED, FAILURE }
enum class StatusRecover { SUCCESS, EMPTY, FAILURE }
enum class PointerStart { MENUINICIAL, MENUAPONT }
enum class FlagUpdate { OUTDATED, UPDATED }
enum class TypeAddEquip { ADDVEICULO, ADDVEICULOSEG, CHANGEVEICULO, CHANGEVEICULOSEG }
enum class TypeAddOcupante { ADDMOTORISTA, ADDPASSAGEIRO, CHANGEMOTORISTA, CHANGEPASSAGEIRO }
enum class TypeVisitTerc { VISITANTE, TERCEIRO }
enum class FlowApp { ADD, CHANGE }
