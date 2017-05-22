/*
 *  Copyright 2010 Yuri Kanivets
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.zipingfang.jindiexuan.module_login.adapter;

import android.content.Context;

import com.zipingfang.jindiexuan.module_login.model.SexUtils;
import com.zipingfang.jindiexuan.view.wheelview.BaseWheelAdapter;

import java.util.List;

public class SexWheelAdapter extends BaseWheelAdapter<SexUtils> {
	public SexWheelAdapter(Context context, List<SexUtils> list) {
		super(context,list);
	}

	@Override
	protected CharSequence getItemText(int index) {
		SexUtils data = getItemData(index);
		if(data != null){
			return data.getData();
		}
		return null;
	}
}
