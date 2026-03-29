package com.jobfair.api.controller;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.jobfair.api.dto.response.CommitteeMemberResponse;
import com.jobfair.domain.service.CommitteeMemberService;
import com.jobfair.shared.response.ApiResponse;

@ExtendWith(MockitoExtension.class)
class CommitteeMemberControllerTest {

    @Mock
    private CommitteeMemberService committeeMemberService;

    private CommitteeMemberController controller;

    @BeforeEach
    void setUp() {
        controller = new CommitteeMemberController(committeeMemberService);
    }

    @Test
    void committeeDelegatesToService() {
        when(committeeMemberService.getByCommitteeId("c1")).thenReturn(List.of());

        ResponseEntity<ApiResponse<List<CommitteeMemberResponse>>> response = controller.committee("c1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(committeeMemberService).getByCommitteeId("c1");
    }

    @Test
    void personDelegatesToService() {
        when(committeeMemberService.getByPersonId("p1")).thenReturn(List.of());

        ResponseEntity<ApiResponse<List<CommitteeMemberResponse>>> response = controller.person("p1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(committeeMemberService).getByPersonId("p1");
    }

    @Test
    void filterConvertsBlankStringsToNull() {
        controller.filter(" ", "  ", "   ");

        verify(committeeMemberService).filter(null, null, null);
    }
}
