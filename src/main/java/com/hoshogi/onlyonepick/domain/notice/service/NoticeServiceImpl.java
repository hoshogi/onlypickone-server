package com.hoshogi.onlyonepick.domain.notice.service;

import com.hoshogi.onlyonepick.domain.member.entity.Member;
import com.hoshogi.onlyonepick.domain.member.service.MemberService;
import com.hoshogi.onlyonepick.domain.notice.dto.request.CreateNoticeRequest;
import com.hoshogi.onlyonepick.domain.notice.dto.request.SearchNoticeCondition;
import com.hoshogi.onlyonepick.domain.notice.dto.response.NoticeResponse;
import com.hoshogi.onlyonepick.domain.notice.entity.Notice;
import com.hoshogi.onlyonepick.domain.notice.repository.NoticeRepository;
import com.hoshogi.onlyonepick.global.error.ErrorCode;
import com.hoshogi.onlyonepick.global.error.exception.BadRequestException;
import com.hoshogi.onlyonepick.global.error.exception.ForbiddenException;
import com.hoshogi.onlyonepick.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {

    private final MemberService memberService;
    private final NoticeRepository noticeRepository;

    @Override
    @Transactional
    public void createNotice(CreateNoticeRequest request) {
        Member member = memberService.findById(SecurityUtil.getCurrentMemberId());
        if (member.isNotAdmin()) {
            throw new ForbiddenException(ErrorCode.FORBIDDEN_USER);
        }
        noticeRepository.save(request.toEntity(member));
    }

    @Override
    @Transactional(readOnly = true)
    public Slice<NoticeResponse> searchNotices(SearchNoticeCondition condition, Pageable pageable) {
        return noticeRepository.search(condition, pageable).map(notice ->
                NoticeResponse.of(notice, false));
    }

    @Override
    @Transactional
    public NoticeResponse showNoticeInfo(Long noticeId) {
        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new BadRequestException(ErrorCode.NOTICE_NOT_FOUND));
        notice.increaseViewCount();
        return NoticeResponse.of(notice, true);
    }

    @Override
    @Transactional
    public void updateNoticeInfo(CreateNoticeRequest request) {

    }

    @Override
    @Transactional
    public void deleteNotice(Long noticeId) {

    }
}