/**
 * Balero CMS v2 Project: Proyecto 100% Mexicano de código libre.
 *
 * @author      Anibal Gomez <anibalgomez@icloud.com>
 * @copyright   Copyright (C) 2015 Neblina Software. Derechos reservados.
 * @license     Licencia Pública GNU versión 3 o superior; vea LICENSE.txt
 */

package com.neblina.balero.web.authorized;

import com.neblina.balero.service.HomepageService;
import com.neblina.balero.service.SettingsService;
import com.neblina.balero.service.repository.HomepageRepository;
import com.neblina.balero.service.repository.SettingsRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/homepage")
public class HomepageController {

    private static final Logger log = LogManager.getLogger(HomepageController.class.getName());

    @Autowired
    private HomepageService homepageService;

    @Autowired
    private HomepageRepository homepageRepository;

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = {"", "/"} )
    public String homepage(Model model) {
        model.addAttribute("homepage", homepageRepository.findAll());
        return "authorized/homepage";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String homepageEditGet(Model model, @PathVariable("id") Long id) {
        model.addAttribute("homepage", homepageRepository.findOneById(id));
        return "authorized/homepage_save";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String homepageEditPost(Model model, @PathVariable("id") Long id,
                                   String name,
                                   String content,
                                   String code) {
        homepageService.saveBlock(
                id,
                name,
                content,
                code
        );
        model.addAttribute("success", 1);
        model.addAttribute("homepage", homepageRepository.findOneById(id));
        return "authorized/homepage_save";
    }

}
