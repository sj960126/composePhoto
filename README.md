# Kotlin Coroutine Flow 관련

## 1. ColdFlow와 HotFlow에 대해 자세히 설명해주세요.

### ColdFlow
- **ColdFlow**는 데이터의 생산이 소비자에 의해 시작되는 흐름입니다.
- 소비자가 Flow를 구독할 때마다 새로운 데이터 생산이 이루어집니다.
- 소비자가 구독하지 않으면 데이터 생산이 발생하지 않습니다.

### HotFlow
- **HotFlow**는 데이터가 이미 생산되고 있는 상태에서 소비자가 구독하는 흐름입니다.
- 모든 수집자는 동일한 데이터를 공유하며, 업데이트가 있을 때 모든 소비자에게 전달됩니다.
- 데이터 생산이 소비자의 구독 상태와 상관없이 계속됩니다.

## 2. StateFlow와 SharedFlow에 대해 자세히 설명해주세요.

### StateFlow
- **StateFlow**는 상태 관리를 위한 HotFlow입니다.
- 항상 최신 상태를 보유하고 있으며, 소비자가 구독할 때 현재 상태를 즉시 제공합니다.
- 상태 변화가 있을 때마다 최신 상태를 모든 소비자에게 전달합니다.

### SharedFlow
- **SharedFlow**는 여러 소비자가 동시에 구독할 수 있는 공유된 데이터 스트림입니다.
- 데이터가 발행되면 모든 소비자에게 전달됩니다.
- 상태를 보존하지 않으며, 최근 이벤트만을 공유합니다.

# Android ViewModel 관련

## 1. owner에 대해 자세히 설명해 주세요.

- **owner**는 ViewModel이 관리하는 데이터와 상태를 보존하는 Activity나 Fragment를 지칭합니다.
- ViewModel은 owner에 의해 관리되며, owner의 생명 주기가 끝나지 않는 한 동일한 ViewModelStore를 공유합니다.
- **ViewModelStore**는 ViewModel 인스턴스를 저장하는 클래스이며, 내부적으로 Map 구조로 되어 있습니다.
- owner가 파괴되지 않는 한, ViewModelStore는 동일성을 유지합니다.

## 2. Compose Navigation과 Dagger Hilt를 같이 사용하는 경우 hiltViewModel로 ViewModel instance를 가져올 때 owner를 어떻게 설정해야 하는지 자세히 설명해주세요.

- ViewModel의 owner는 현재 Composable 함수가 호출되는 **NavBackStackEntry**로 설정됩니다.
- 이 과정에서 Hilt는 Composable의 생명 주기를 관리하는 NavController와 연결하여 적절한 ViewModel을 생성하고 제공합니다.

# Android Paging3 관련

## 1. PagingSource와 getRefreshKey 함수의 파라미터와 리턴 값에 대해 자세히 설명해주세요.

### PagingSource
- **PagingSource**는 Paging3 라이브러리에서 페이지 단위로 데이터를 로드하는 데 사용되는 클래스입니다.

### getRefreshKey
- **getRefreshKey**는 PagingSource에서 페이지를 새로 고침할 때 필요한 키를 반환하는 메서드입니다..

- **파라미터**:
  - `state`: `PagingState<Key, Value>`로 현재 Paging의 상태입니다.
    - `anchors`: 현재의 Paging 상태에서 페이지가 로드된 위치에 대한 정보입니다.
    - `pages`: 현재 페이지들에 대한 정보입니다.
    - `config`: Paging 설정, 예를 들어 페이지 크기와 같은 구성 정보입니다.
  - 이 `state`는 Paging 라이브러리가 현재 어떤 페이지를 보고 있는지, 어떤 페이지를 로드했는지에 대한 정보를 제공하며, 이를 기반으로 새로고침할 때 필요한 키를 결정하는 데 사용됩니다.

- **리턴 값**:
  - 새로 고침할 때 사용할 수 있는 키를 리턴합니다.
  - 새로 고침이 필요가 없다면 `null`을 리턴합니다.

## 2. PagingSource의 load 함수의 파라미터와 리턴 값에 대해 설명해주세요.

### load 함수
- **load** 함수는 실제 데이터를 로드하는 역할을 합니다.

### 파라미터
- `LoadParams<Key>`:
  - `key`: 로드할 페이지의 키를 나타내며, 다음 페이지를 로드할 때 필요한 정보입니다. 예를 들어, 네트워크 요청에서는 페이지 번호, 데이터베이스 쿼리에서는 오프셋 값이 될 수 있습니다.
  - `loadSize`: 로드할 데이터의 크기, 즉 몇 개의 항목을 로드할 것인지를 지정합니다.
  - `placeholdersEnabled`: 데이터가 로드되는 동안 자리 표시자를 사용할지 여부를 나타내는 플래그입니다.

### 리턴 값
- `LoadResult<Key, Value>`를 반환하며, 이는 로드 작업의 성공 또는 실패를 나타냅니다.
  - `LoadResult.Page<Key, Value>`: 로드가 성공했을 때 반환됩니다.
    - `data`: 로드된 데이터 목록입니다.
    - `prevKey`: 이전 페이지의 키로, null이면 이전 페이지가 없음을 의미합니다.
    - `nextKey`: 다음 페이지의 키로, null이면 다음 페이지가 없음을 의미합니다.
  - `LoadResult.Error`: 로드가 실패했을 때 반환됩니다.
    - `throwable`: 로드 실패의 원인이 되는 예외입니다.
