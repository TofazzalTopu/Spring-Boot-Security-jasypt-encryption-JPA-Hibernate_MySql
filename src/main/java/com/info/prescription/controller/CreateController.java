package com.info.prescription.controller;

import com.info.prescription.model.Prescription;
import com.info.prescription.service.PrescriptionCountReport;
import com.info.prescription.service.PrescriptionService;
import com.info.prescription.utility.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping(value = "/")
public class CreateController {
    private static final Logger logger = LoggerFactory.getLogger(CreateController.class);

    @Autowired
    PrescriptionService prescriptionService;

    // @Comment: This method will render form to create prescription.
    @RequestMapping(method = RequestMethod.GET, value = {"/prescription/createForm"})
    public String createPrescriptionForm(Model model) {
        Prescription prescription = new Prescription();
        model.addAttribute("prescription", prescription);
        logger.info("Create prescription Form Loaded.");
        return "prescription/create_prescription";
    }

    @ModelAttribute("dateFormat")
    public String dateFormat() {
        return "yyyy-MM-dd";
    }

    @InitBinder
    private void dateBinder(WebDataBinder binder) {
        //The date format to parse or output your dates
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormat());
        //Create a new CustomDateEditor
        CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
        //Register it as custom editor for the Date type
        binder.registerCustomEditor(Date.class, editor);
    }

    // @Comment: This method will create prescription and will render prescription list form after create prescription.
    @PostMapping("/prescription/create")
    public String createPrescription(Model model, @ModelAttribute("prescription") Prescription prescription
            , BindingResult result) throws IOException {
        try {
            if (result.hasErrors()) {
                logger.info("Prescription not saved!");
                model.addAttribute("error", "Please enter all mendatory fields!");
                return "prescription/create_prescription";
            }
            if (prescription.getGender() == null || prescription.getGender() == "") {
                logger.info("Prescription not saved!");
                model.addAttribute("error", "Please select gender!");
                return "prescription/create_prescription";
            }
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
            String prDate = formatter.format(prescription.getPrescriptionDate());
            String createDate = formatter.format(new Date());
            prescription.setCreateDate(formatter.parse(createDate));
            prescription.setPrescriptionDate(formatter.parse(prDate));

            if (prescription.getNextVisitDate() != null) {
                String nVisitDate = formatter.format(prescription.getNextVisitDate());
                prescription.setNextVisitDate(formatter.parse(nVisitDate));
            }

            Prescription _prescription = prescriptionService.save(prescription);
            if (_prescription == null) {
                logger.info("Prescription not saved!");
                model.addAttribute("error", "Prescription not saved!");
                return "prescription/create_prescription";
            }

            logger.info("Prescription Created successfully.");

            List<Prescription> prescriptionList = prescriptionService.findAll();

            logger.info("Fetching PrescriptionList.");
            model.addAttribute("prescription", _prescription);
            model.addAttribute("prescriptionList", prescriptionList);
            model.addAttribute("success", "Prescription Created successfully.");
            return "prescription/view_prescription";
        } catch (Exception e) {
            model.addAttribute("error", "Please enter all mendatory fields!");
            return "prescription/create_prescription";
        }
    }

    // @Comment: This method will render prescription list based on date range.
    @RequestMapping(method = RequestMethod.GET, value = {"/prescription/list"})
    public String prescriptionList(Model model, @RequestParam(required = false, defaultValue = "") String range) throws IOException {

        List<Prescription> prescriptionList = new ArrayList<>();
        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTimeZone(TimeZone.getTimeZone("GMT"));
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = 1;
            cal.set(year, month, day);
            int numOfDaysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
            String firstDay = formatter.format(cal.getTime());
            String firstDayWithMin = firstDay + " 00:00:00.000";

            cal.add(Calendar.DAY_OF_MONTH, numOfDaysInMonth - 1);
            String lastDay = formatter.format(cal.getTime());
            String lastDayWithMin = lastDay + " 00:00:00.000";

            Date fromDay;
            Date toDay;

            if (StringUtil.isEmptyString(range) || range.length() != 21) {
                fromDay = formatter2.parse(firstDayWithMin);
                toDay = formatter2.parse(lastDayWithMin);
            } else {
                String range1 = range.split("_")[0] + " 00:00:00.000";
                String range2 = range.split("_")[1] + " 23:59:59.999";
                fromDay = formatter2.parse(range1);
                toDay = formatter2.parse(range2);
            }
            System.out.println("fromDay date: " + fromDay);
            System.out.println("toDay date: " + toDay
            );
            prescriptionList = prescriptionService.getAllPrescription(range, fromDay, toDay);

            prescriptionList.forEach(prescription -> {
                System.out.println(prescription.getId());
            });

            if (prescriptionList.isEmpty()) {
                model.addAttribute("error", "No data found for this selected date range!");
            } else {
                model.addAttribute("prescriptionList", prescriptionList);
            }
            return "prescription/prescription_list";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error during fetching data!");
            return "prescription/prescription_list";
        }
    }

    @RequestMapping(value = "/prescription/viewDetails/{id}", method = RequestMethod.GET)
    public String viewDetails(Model model, @PathVariable Long id) {
        Prescription prescription = prescriptionService.findById(id);
        try {
            model.addAttribute("prescription", prescription);
            return "prescription/view_prescription";
        } catch (Exception e) {
            model.addAttribute("prescription", prescription);
            return "prescription/prescription_list";
        }
    }

    @RequestMapping(value = "/prescription/edit/{id}", method = RequestMethod.GET)
    public String edit(Model model, @PathVariable Long id) {
        try {
            Prescription prescription = prescriptionService.findById(id);

            model.addAttribute("prescription", prescription);
            return "prescription/edit_prescription";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "ERROR - Prescription not found!");
            return "prescription/edit_prescription";
        }
    }

    // @Comment: This method will create prescription and will render prescription list form after create prescription.
    @PostMapping("/prescription/update/{id}")
    public String updatePrescription(Model model, @PathVariable Long id, @ModelAttribute("prescription") Prescription prescription,
                                     BindingResult result) throws IOException {
        try {
            if (result.hasErrors()) {
                logger.info("Prescription not update!");
                model.addAttribute("error", "Please enter all mendatory fields!");
                return "prescription/edit_prescription";
            }
            if (prescription.getGender() == null || prescription.getGender() == "") {
                logger.info("Prescription not saved!");
                model.addAttribute("error", "Please select gender!");
                return "prescription/edit_prescription";
            }

            SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
            String prDate = formatter.format(prescription.getPrescriptionDate());
            String createDate = formatter.format(new Date());
            prescription.setCreateDate(formatter.parse(createDate));
            prescription.setPrescriptionDate(formatter.parse(prDate));

            if (prescription.getNextVisitDate() != null) {
                String nVisitDate = formatter.format(prescription.getNextVisitDate());
                prescription.setNextVisitDate(formatter.parse(nVisitDate));
            }

            Prescription _prescription = prescriptionService.save(prescription);
            if (_prescription == null) {
                logger.info("Prescription not saved!");
                model.addAttribute("error", "Prescription not saved!");
                return "prescription/edit_prescription";
            }

            logger.info("Prescription updated successfully.");

            List<Prescription> prescriptionList = prescriptionService.findAll();

            logger.info("Fetching PrescriptionList.");
            model.addAttribute("prescriptionList", prescriptionList);
            model.addAttribute("success", "Prescription updated successfully.");
            return "prescription/view_prescription";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "ERROR - Prescription not saved!");
            return "prescription/edit_prescription";
        }
    }

    // @Comment: This method will render prescription list form.
    @RequestMapping(method = RequestMethod.GET, value = {"/prescription/report/list"})
    public String prescriptionCountReport(Model model) {
        try {
            List<PrescriptionCountReport> prescriptionCountReportList = prescriptionService.findByPrescriptionDateAndCount();
            if (prescriptionCountReportList.isEmpty()) {
                model.addAttribute("error", "No data found.");
            } else {
                model.addAttribute("prescriptionCountReportList", prescriptionCountReportList);
            }
            return "prescription/prescription_report_list";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Prescription not found!");
            return "prescription/prescription_report_list";
        }
    }

    @RequestMapping(value = "/prescription/delete/{id}", method = RequestMethod.GET)
    public String delete(Model model, @PathVariable Long id) {
        try {
            Prescription prescription = prescriptionService.findById(id);
            boolean isDelete = prescriptionService.deletePrescription(prescription);

            if (isDelete) {
                model.addAttribute("success", "Prescription deleted successfully!");
                return "prescription/prescription_list";
            } else {
                model.addAttribute("error", "ERROR - Prescription not deleted!");
                return "prescription/prescription_list";
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "ERROR - Prescription not deleted!");
            return "prescription/prescription_list";
        }
    }

}
