package run.halo.app.controller.admin.api;

import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import run.halo.app.model.dto.JournalDTO;
import run.halo.app.model.dto.JournalWithCmtCountDTO;
import run.halo.app.model.entity.Journal;
import run.halo.app.model.params.JournalParam;
import run.halo.app.service.JournalService;

import javax.validation.Valid;
import java.util.List;

/**
 * Journal controller.
 *
 * @author johnniang
 * @date 19-4-25
 */
@RestController
@RequestMapping("/api/admin/journals")
public class JournalController {

    private final JournalService journalService;

    public JournalController(JournalService journalService) {
        this.journalService = journalService;
    }

    @GetMapping
    @ApiOperation("Gets latest journals")
    public Page<JournalWithCmtCountDTO> pageBy(Pageable pageable) {
        Page<Journal> journalPage = journalService.listAll(pageable);
        return journalService.convertToCmtCountDto(journalPage);
    }

    @GetMapping("latest")
    @ApiOperation("Gets latest journals")
    public List<JournalWithCmtCountDTO> pageLatest(@RequestParam(name = "top", defaultValue = "10") int top) {
        List<Journal> journals = journalService.pageLatest(top).getContent();
        return journalService.convertToCmtCountDto(journals);
    }

    @PostMapping
    @ApiOperation("Creates a journal")
    public JournalDTO createBy(@RequestBody @Valid JournalParam journalParam) {
        Journal createdJournal = journalService.createBy(journalParam);
        return journalService.convertTo(createdJournal);
    }

}
