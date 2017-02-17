package graphQL.data

import graphQL.schemas.Printer.Setting
import graphQL.schemas.Printer

class PrintersData {
  private val transport = List(Setting("transportName","Demo"),Setting("baud","31"))
  private var PrinterList = List(
    Printer(
      name = "Koseel",
      transport = transport
    )
  )

  def list: List[Printer] = PrinterList
  def addPrinter(printer:Printer): Printer ={
    PrinterList = printer :: PrinterList
    printer
  }
}
