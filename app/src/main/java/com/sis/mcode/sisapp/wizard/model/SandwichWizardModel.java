package com.sis.mcode.sisapp.wizard.model;

import android.content.Context;

import com.sis.mcode.sisapp.wizard.model.AbstractWizardModel;
import com.sis.mcode.sisapp.wizard.model.BranchPage;
import com.sis.mcode.sisapp.wizard.model.CustomerDocumentPage;
import com.sis.mcode.sisapp.wizard.model.CustomerInfoPage;
import com.sis.mcode.sisapp.wizard.model.PageList;
import com.sis.mcode.sisapp.wizard.model.SingleFixedChoicePage;

public class SandwichWizardModel extends AbstractWizardModel {

    public SandwichWizardModel(Context context) {
        super(context);
    }

    @Override
    protected PageList onNewRootPageList() {
        return new PageList(

                // BranchPage shows all of the branches available: Branch One, Branch Two, Branch Three. Each of these branches
                // have their own questions and the choices of the user will be summarised in the review section at the end
                new BranchPage(this, "Tipo de consulta")
                        .addBranch("Datos Personales",
                                new CustomerInfoPage(this, "Datos personales")
                                        .setRequired(true)
                        )

                                // Second branch of questions
                        .addBranch("Tipo de documento",
                                new SingleFixedChoicePage(this, "Tipo de documento")
                                        .setChoices("DNI", "Carne de extranjería")
                                        .setRequired(true),
                                new CustomerDocumentPage(this, "Número Documento")
                                        .setRequired(true)
                        )
        );
    }

}