package br.com.usinasantafe.pcp.model.pst;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import br.com.usinasantafe.pcp.model.bean.estaticas.ColabBean;
import br.com.usinasantafe.pcp.model.bean.estaticas.EquipBean;
import br.com.usinasantafe.pcp.model.bean.estaticas.LocalBean;
import br.com.usinasantafe.pcp.model.bean.estaticas.TerceiroBean;
import br.com.usinasantafe.pcp.model.bean.estaticas.VisitanteBean;
import br.com.usinasantafe.pcp.model.bean.variaveis.ConfigBean;
import br.com.usinasantafe.pcp.model.bean.variaveis.LogErroBean;
import br.com.usinasantafe.pcp.model.bean.variaveis.LogProcessoBean;
import br.com.usinasantafe.pcp.model.bean.variaveis.MovEquipProprioBean;
import br.com.usinasantafe.pcp.model.bean.variaveis.MovEquipResidenciaBean;
import br.com.usinasantafe.pcp.model.bean.variaveis.MovEquipProprioSegBean;
import br.com.usinasantafe.pcp.model.bean.variaveis.MovEquipVisitTercBean;
import br.com.usinasantafe.pcp.model.dao.LogErroDAO;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

	public static final String FORCA_DB_NAME = "pcp_db";
	public static final int FORCA_BD_VERSION = 3;

	private static DatabaseHelper instance;
	
	public static DatabaseHelper getInstance(){
		return instance;
	}
	
	public DatabaseHelper(Context context) {
		super(context, FORCA_DB_NAME, null, FORCA_BD_VERSION);
		instance = this;
	}

	@Override
	public void close() {
		super.close();
		instance = null;
	}
	
	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource cs) {
		
		try{
			createAllInitial(cs);
		}
		catch(Exception e){
			Log.e(DatabaseHelper.class.getName(),
					"Erro criando banco de dados...",
					e);
		}
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db,
			ConnectionSource cs,
			int oldVersion,
			int newVersion) {

		if((oldVersion == 1) && (newVersion == 2)){
			dropAllInitial(cs);
			createAllInitial(cs);
		} else if((oldVersion == 2) && (newVersion == 3)){
			dropAllInitial(cs);
			createAllInitial(cs);
		}

	}

	public void dropAllInitial(ConnectionSource cs){

		try {

			TableUtils.dropTable(cs, ColabBean.class, true);
			TableUtils.dropTable(cs, EquipBean.class, true);
			TableUtils.dropTable(cs, LocalBean.class, true);
			TableUtils.dropTable(cs, TerceiroBean.class, true);
			TableUtils.dropTable(cs, VisitanteBean.class, true);

			TableUtils.dropTable(cs, ConfigBean.class, true);
			TableUtils.dropTable(cs, LogErroBean.class, true);
			TableUtils.dropTable(cs, LogProcessoBean.class, true);
			TableUtils.dropTable(cs, MovEquipProprioBean.class, true);
			TableUtils.dropTable(cs, MovEquipProprioSegBean.class, true);
			TableUtils.dropTable(cs, MovEquipVisitTercBean.class, true);

		} catch (Exception e) {
			LogErroDAO.getInstance().insertLogErro(e);
			Log.e(DatabaseHelper.class.getName(), "Erro atualizando banco de dados...", e);
		}

	}

	public void createAllInitial(ConnectionSource cs){

		try {

			TableUtils.createTable(cs, ColabBean.class);
			TableUtils.createTable(cs, EquipBean.class);
			TableUtils.createTable(cs, LocalBean.class);
			TableUtils.createTable(cs, TerceiroBean.class);
			TableUtils.createTable(cs, VisitanteBean.class);

			TableUtils.createTable(cs, ConfigBean.class);
			TableUtils.createTable(cs, LogErroBean.class);
			TableUtils.createTable(cs, LogProcessoBean.class);
			TableUtils.createTable(cs, MovEquipProprioBean.class);
			TableUtils.createTable(cs, MovEquipProprioSegBean.class);
			TableUtils.createTable(cs, MovEquipVisitTercBean.class);
			TableUtils.createTable(cs, MovEquipResidenciaBean.class);

		} catch (Exception e) {
			LogErroDAO.getInstance().insertLogErro(e);
			Log.e(DatabaseHelper.class.getName(), "Erro atualizando banco de dados...", e);
		}

	}


}
