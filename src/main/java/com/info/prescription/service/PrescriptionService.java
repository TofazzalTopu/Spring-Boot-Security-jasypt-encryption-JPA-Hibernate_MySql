package com.info.prescription.service;

import com.info.prescription.model.Prescription;
import com.info.prescription.repository.PrescriptionRepository;
import com.info.prescription.utility.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PrescriptionService {

    @Autowired
    PrescriptionRepository prescriptionRepository;


    public Prescription save(Prescription prescription){
        return prescriptionRepository.save(prescription);
    }

    public List<Prescription> findAll(){
        return prescriptionRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    public List<Prescription> getAllPrescription(String range, Date fromDate, Date toDate) {
        List<Prescription> prescriptionList = new ArrayList<>();

        if (StringUtil.isEmptyString(range) || range.length() != 21) {
            prescriptionList = prescriptionRepository.findAll(Sort.by(Sort.Direction.DESC, "prescriptionDate"));
        } else {
            prescriptionList = prescriptionRepository.findByPrescriptionDateBetweenOrderByPrescriptionDateDesc(fromDate, toDate);

        }
        return prescriptionList;
    }

    public Prescription findById(Long id){
        return prescriptionRepository.findById(id).get();
    }

    public boolean deletePrescription(Prescription prescription){
        boolean isDelete = true;
        try {
            prescriptionRepository.delete(prescription);
        }catch (Exception e){
            isDelete = false;
            e.printStackTrace();
        }
        return isDelete;
    }

    public List<PrescriptionCountReport> findByPrescriptionDateAndCount(){
        return prescriptionRepository.findAllPrescriptions();
    }


}
