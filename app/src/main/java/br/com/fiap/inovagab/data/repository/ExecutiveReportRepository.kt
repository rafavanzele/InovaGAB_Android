package br.com.fiap.inovagab.data.repository

import br.com.fiap.inovagab.data.mock.ExecutiveReportMock
import br.com.fiap.inovagab.data.model.ExecutiveReport

class ExecutiveReportRepository {

    fun getExecutiveReports(): List<ExecutiveReport> {
        return ExecutiveReportMock.executiveReports
    }
}