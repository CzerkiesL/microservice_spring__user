package com.ludo.ms_user.service_interface.administration;

import java.util.List;

import com.ludo.ms_user.dto.admin_api.AdminBannedUserDto;

public interface BannedUserAdminServiceInt {

	AdminBannedUserDto getBannedUserByBanId(String banId);

	List<AdminBannedUserDto> getAllBannedUser();

	AdminBannedUserDto updateBannedUserDuration(String banId, String duration);

	String clearBannedUser();

}
